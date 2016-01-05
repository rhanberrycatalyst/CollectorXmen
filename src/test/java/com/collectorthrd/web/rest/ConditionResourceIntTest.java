package com.collectorthrd.web.rest;

import com.collectorthrd.Application;
import com.collectorthrd.domain.Condition;
import com.collectorthrd.repository.ConditionRepository;
import com.collectorthrd.service.ConditionService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ConditionResource REST controller.
 *
 * @see ConditionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConditionResourceIntTest {

    private static final String DEFAULT_CONDITION = "A";
    private static final String UPDATED_CONDITION = "B";

    @Inject
    private ConditionRepository conditionRepository;

    @Inject
    private ConditionService conditionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConditionMockMvc;

    private Condition condition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConditionResource conditionResource = new ConditionResource();
        ReflectionTestUtils.setField(conditionResource, "conditionService", conditionService);
        this.restConditionMockMvc = MockMvcBuilders.standaloneSetup(conditionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        condition = new Condition();
        condition.setCondition(DEFAULT_CONDITION);
    }

    @Test
    @Transactional
    public void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // Create the Condition

        restConditionMockMvc.perform(post("/api/conditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condition)))
                .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditions.get(conditions.size() - 1);
        assertThat(testCondition.getCondition()).isEqualTo(DEFAULT_CONDITION);
    }

    @Test
    @Transactional
    public void checkConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = conditionRepository.findAll().size();
        // set the field null
        condition.setCondition(null);

        // Create the Condition, which fails.

        restConditionMockMvc.perform(post("/api/conditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condition)))
                .andExpect(status().isBadRequest());

        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditions
        restConditionMockMvc.perform(get("/api/conditions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
                .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())));
    }

    @Test
    @Transactional
    public void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get the condition
        restConditionMockMvc.perform(get("/api/conditions/{id}", condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(condition.getId().intValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get("/api/conditions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

		int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        condition.setCondition(UPDATED_CONDITION);

        restConditionMockMvc.perform(put("/api/conditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condition)))
                .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditions.get(conditions.size() - 1);
        assertThat(testCondition.getCondition()).isEqualTo(UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

		int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Get the condition
        restConditionMockMvc.perform(delete("/api/conditions/{id}", condition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
