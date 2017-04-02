package com.fangzhou.manatee.web.rest;

import com.fangzhou.manatee.ManateeApp;

import com.fangzhou.manatee.domain.ReferralSource;
import com.fangzhou.manatee.repository.ReferralSourceRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReferralSourceResource REST controller.
 *
 * @see ReferralSourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManateeApp.class)
public class ReferralSourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    @Inject
    private ReferralSourceRepository referralSourceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReferralSourceMockMvc;

    private ReferralSource referralSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReferralSourceResource referralSourceResource = new ReferralSourceResource();
        ReflectionTestUtils.setField(referralSourceResource, "referralSourceRepository", referralSourceRepository);
        this.restReferralSourceMockMvc = MockMvcBuilders.standaloneSetup(referralSourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferralSource createEntity(EntityManager em) {
        ReferralSource referralSource = new ReferralSource()
                .name(DEFAULT_NAME)
                .contact(DEFAULT_CONTACT);
        return referralSource;
    }

    @Before
    public void initTest() {
        referralSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferralSource() throws Exception {
        int databaseSizeBeforeCreate = referralSourceRepository.findAll().size();

        // Create the ReferralSource

        restReferralSourceMockMvc.perform(post("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSource)))
            .andExpect(status().isCreated());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeCreate + 1);
        ReferralSource testReferralSource = referralSourceList.get(referralSourceList.size() - 1);
        assertThat(testReferralSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReferralSource.getContact()).isEqualTo(DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    public void createReferralSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referralSourceRepository.findAll().size();

        // Create the ReferralSource with an existing ID
        ReferralSource existingReferralSource = new ReferralSource();
        existingReferralSource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferralSourceMockMvc.perform(post("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReferralSource)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReferralSources() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);

        // Get all the referralSourceList
        restReferralSourceMockMvc.perform(get("/api/referral-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referralSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())));
    }

    @Test
    @Transactional
    public void getReferralSource() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);

        // Get the referralSource
        restReferralSourceMockMvc.perform(get("/api/referral-sources/{id}", referralSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referralSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferralSource() throws Exception {
        // Get the referralSource
        restReferralSourceMockMvc.perform(get("/api/referral-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferralSource() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);
        int databaseSizeBeforeUpdate = referralSourceRepository.findAll().size();

        // Update the referralSource
        ReferralSource updatedReferralSource = referralSourceRepository.findOne(referralSource.getId());
        updatedReferralSource
                .name(UPDATED_NAME)
                .contact(UPDATED_CONTACT);

        restReferralSourceMockMvc.perform(put("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReferralSource)))
            .andExpect(status().isOk());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeUpdate);
        ReferralSource testReferralSource = referralSourceList.get(referralSourceList.size() - 1);
        assertThat(testReferralSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReferralSource.getContact()).isEqualTo(UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void updateNonExistingReferralSource() throws Exception {
        int databaseSizeBeforeUpdate = referralSourceRepository.findAll().size();

        // Create the ReferralSource

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReferralSourceMockMvc.perform(put("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSource)))
            .andExpect(status().isCreated());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReferralSource() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);
        int databaseSizeBeforeDelete = referralSourceRepository.findAll().size();

        // Get the referralSource
        restReferralSourceMockMvc.perform(delete("/api/referral-sources/{id}", referralSource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
