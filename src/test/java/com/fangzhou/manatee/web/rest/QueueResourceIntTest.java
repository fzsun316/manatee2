package com.fangzhou.manatee.web.rest;

import com.fangzhou.manatee.ManateeApp;

import com.fangzhou.manatee.domain.Queue;
import com.fangzhou.manatee.repository.QueueRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.fangzhou.manatee.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QueueResource REST controller.
 *
 * @see QueueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManateeApp.class)
public class QueueResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP_INITIAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP_INITIAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TIMESTAMP_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP_FINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private QueueRepository queueRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQueueMockMvc;

    private Queue queue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QueueResource queueResource = new QueueResource();
        ReflectionTestUtils.setField(queueResource, "queueRepository", queueRepository);
        this.restQueueMockMvc = MockMvcBuilders.standaloneSetup(queueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Queue createEntity(EntityManager em) {
        Queue queue = new Queue()
                .status(DEFAULT_STATUS)
                .timestampInitial(DEFAULT_TIMESTAMP_INITIAL)
                .timestampFinal(DEFAULT_TIMESTAMP_FINAL);
        return queue;
    }

    @Before
    public void initTest() {
        queue = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueue() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue

        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isCreated());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeCreate + 1);
        Queue testQueue = queueList.get(queueList.size() - 1);
        assertThat(testQueue.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testQueue.getTimestampInitial()).isEqualTo(DEFAULT_TIMESTAMP_INITIAL);
        assertThat(testQueue.getTimestampFinal()).isEqualTo(DEFAULT_TIMESTAMP_FINAL);
    }

    @Test
    @Transactional
    public void createQueueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue with an existing ID
        Queue existingQueue = new Queue();
        existingQueue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQueue)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQueues() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get all the queueList
        restQueueMockMvc.perform(get("/api/queues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queue.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].timestampInitial").value(hasItem(sameInstant(DEFAULT_TIMESTAMP_INITIAL))))
            .andExpect(jsonPath("$.[*].timestampFinal").value(hasItem(sameInstant(DEFAULT_TIMESTAMP_FINAL))));
    }

    @Test
    @Transactional
    public void getQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", queue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queue.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.timestampInitial").value(sameInstant(DEFAULT_TIMESTAMP_INITIAL)))
            .andExpect(jsonPath("$.timestampFinal").value(sameInstant(DEFAULT_TIMESTAMP_FINAL)));
    }

    @Test
    @Transactional
    public void getNonExistingQueue() throws Exception {
        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);
        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Update the queue
        Queue updatedQueue = queueRepository.findOne(queue.getId());
        updatedQueue
                .status(UPDATED_STATUS)
                .timestampInitial(UPDATED_TIMESTAMP_INITIAL)
                .timestampFinal(UPDATED_TIMESTAMP_FINAL);

        restQueueMockMvc.perform(put("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQueue)))
            .andExpect(status().isOk());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeUpdate);
        Queue testQueue = queueList.get(queueList.size() - 1);
        assertThat(testQueue.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testQueue.getTimestampInitial()).isEqualTo(UPDATED_TIMESTAMP_INITIAL);
        assertThat(testQueue.getTimestampFinal()).isEqualTo(UPDATED_TIMESTAMP_FINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingQueue() throws Exception {
        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Create the Queue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQueueMockMvc.perform(put("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isCreated());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);
        int databaseSizeBeforeDelete = queueRepository.findAll().size();

        // Get the queue
        restQueueMockMvc.perform(delete("/api/queues/{id}", queue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
