package com.fangzhou.manatee.web.rest;

import com.fangzhou.manatee.ManateeApp;

import com.fangzhou.manatee.domain.Customlayout;
import com.fangzhou.manatee.repository.CustomlayoutRepository;
import com.fangzhou.manatee.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomlayoutResource REST controller.
 *
 * @see CustomlayoutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManateeApp.class)
public class CustomlayoutResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CustomlayoutRepository customlayoutRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomlayoutMockMvc;

    private Customlayout customlayout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomlayoutResource customlayoutResource = new CustomlayoutResource(customlayoutRepository);
        this.restCustomlayoutMockMvc = MockMvcBuilders.standaloneSetup(customlayoutResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customlayout createEntity(EntityManager em) {
        Customlayout customlayout = new Customlayout()
            .title(DEFAULT_TITLE)
            .timestamp(DEFAULT_TIMESTAMP);
        return customlayout;
    }

    @Before
    public void initTest() {
        customlayout = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomlayout() throws Exception {
        int databaseSizeBeforeCreate = customlayoutRepository.findAll().size();

        // Create the Customlayout
        restCustomlayoutMockMvc.perform(post("/api/customlayouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customlayout)))
            .andExpect(status().isCreated());

        // Validate the Customlayout in the database
        List<Customlayout> customlayoutList = customlayoutRepository.findAll();
        assertThat(customlayoutList).hasSize(databaseSizeBeforeCreate + 1);
        Customlayout testCustomlayout = customlayoutList.get(customlayoutList.size() - 1);
        assertThat(testCustomlayout.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCustomlayout.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createCustomlayoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customlayoutRepository.findAll().size();

        // Create the Customlayout with an existing ID
        customlayout.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomlayoutMockMvc.perform(post("/api/customlayouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customlayout)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Customlayout> customlayoutList = customlayoutRepository.findAll();
        assertThat(customlayoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomlayouts() throws Exception {
        // Initialize the database
        customlayoutRepository.saveAndFlush(customlayout);

        // Get all the customlayoutList
        restCustomlayoutMockMvc.perform(get("/api/customlayouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customlayout.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    public void getCustomlayout() throws Exception {
        // Initialize the database
        customlayoutRepository.saveAndFlush(customlayout);

        // Get the customlayout
        restCustomlayoutMockMvc.perform(get("/api/customlayouts/{id}", customlayout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customlayout.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomlayout() throws Exception {
        // Get the customlayout
        restCustomlayoutMockMvc.perform(get("/api/customlayouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomlayout() throws Exception {
        // Initialize the database
        customlayoutRepository.saveAndFlush(customlayout);
        int databaseSizeBeforeUpdate = customlayoutRepository.findAll().size();

        // Update the customlayout
        Customlayout updatedCustomlayout = customlayoutRepository.findOne(customlayout.getId());
        updatedCustomlayout
            .title(UPDATED_TITLE)
            .timestamp(UPDATED_TIMESTAMP);

        restCustomlayoutMockMvc.perform(put("/api/customlayouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomlayout)))
            .andExpect(status().isOk());

        // Validate the Customlayout in the database
        List<Customlayout> customlayoutList = customlayoutRepository.findAll();
        assertThat(customlayoutList).hasSize(databaseSizeBeforeUpdate);
        Customlayout testCustomlayout = customlayoutList.get(customlayoutList.size() - 1);
        assertThat(testCustomlayout.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomlayout.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomlayout() throws Exception {
        int databaseSizeBeforeUpdate = customlayoutRepository.findAll().size();

        // Create the Customlayout

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomlayoutMockMvc.perform(put("/api/customlayouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customlayout)))
            .andExpect(status().isCreated());

        // Validate the Customlayout in the database
        List<Customlayout> customlayoutList = customlayoutRepository.findAll();
        assertThat(customlayoutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomlayout() throws Exception {
        // Initialize the database
        customlayoutRepository.saveAndFlush(customlayout);
        int databaseSizeBeforeDelete = customlayoutRepository.findAll().size();

        // Get the customlayout
        restCustomlayoutMockMvc.perform(delete("/api/customlayouts/{id}", customlayout.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Customlayout> customlayoutList = customlayoutRepository.findAll();
        assertThat(customlayoutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customlayout.class);
        Customlayout customlayout1 = new Customlayout();
        customlayout1.setId(1L);
        Customlayout customlayout2 = new Customlayout();
        customlayout2.setId(customlayout1.getId());
        assertThat(customlayout1).isEqualTo(customlayout2);
        customlayout2.setId(2L);
        assertThat(customlayout1).isNotEqualTo(customlayout2);
        customlayout1.setId(null);
        assertThat(customlayout1).isNotEqualTo(customlayout2);
    }
}
