package com.collectorthrd.web.rest;

import com.collectorthrd.Application;
import com.collectorthrd.domain.Collectible;
import com.collectorthrd.repository.CollectibleRepository;
import com.collectorthrd.service.CollectibleService;

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
 * Test class for the CollectibleResource REST controller.
 *
 * @see CollectibleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CollectibleResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";
    private static final String DEFAULT_DESCRIPTION = "A";
    private static final String UPDATED_DESCRIPTION = "B";

    private static final Double DEFAULT_AGE = 0D;
    private static final Double UPDATED_AGE = 1D;
    private static final String DEFAULT_CATALOGUENUMBER = "AAAAA";
    private static final String UPDATED_CATALOGUENUMBER = "BBBBB";

    @Inject
    private CollectibleRepository collectibleRepository;

    @Inject
    private CollectibleService collectibleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCollectibleMockMvc;

    private Collectible collectible;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollectibleResource collectibleResource = new CollectibleResource();
        ReflectionTestUtils.setField(collectibleResource, "collectibleService", collectibleService);
        this.restCollectibleMockMvc = MockMvcBuilders.standaloneSetup(collectibleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        collectible = new Collectible();
        collectible.setName(DEFAULT_NAME);
        collectible.setDescription(DEFAULT_DESCRIPTION);
        collectible.setAge(DEFAULT_AGE);
        collectible.setCataloguenumber(DEFAULT_CATALOGUENUMBER);
    }

    @Test
    @Transactional
    public void createCollectible() throws Exception {
        int databaseSizeBeforeCreate = collectibleRepository.findAll().size();

        // Create the Collectible

        restCollectibleMockMvc.perform(post("/api/collectibles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(collectible)))
                .andExpect(status().isCreated());

        // Validate the Collectible in the database
        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeCreate + 1);
        Collectible testCollectible = collectibles.get(collectibles.size() - 1);
        assertThat(testCollectible.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollectible.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollectible.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCollectible.getCataloguenumber()).isEqualTo(DEFAULT_CATALOGUENUMBER);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectibleRepository.findAll().size();
        // set the field null
        collectible.setName(null);

        // Create the Collectible, which fails.

        restCollectibleMockMvc.perform(post("/api/collectibles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(collectible)))
                .andExpect(status().isBadRequest());

        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectibleRepository.findAll().size();
        // set the field null
        collectible.setDescription(null);

        // Create the Collectible, which fails.

        restCollectibleMockMvc.perform(post("/api/collectibles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(collectible)))
                .andExpect(status().isBadRequest());

        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectibleRepository.findAll().size();
        // set the field null
        collectible.setAge(null);

        // Create the Collectible, which fails.

        restCollectibleMockMvc.perform(post("/api/collectibles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(collectible)))
                .andExpect(status().isBadRequest());

        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCataloguenumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectibleRepository.findAll().size();
        // set the field null
        collectible.setCataloguenumber(null);

        // Create the Collectible, which fails.

        restCollectibleMockMvc.perform(post("/api/collectibles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(collectible)))
                .andExpect(status().isBadRequest());

        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollectibles() throws Exception {
        // Initialize the database
        collectibleRepository.saveAndFlush(collectible);

        // Get all the collectibles
        restCollectibleMockMvc.perform(get("/api/collectibles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(collectible.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
                .andExpect(jsonPath("$.[*].cataloguenumber").value(hasItem(DEFAULT_CATALOGUENUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCollectible() throws Exception {
        // Initialize the database
        collectibleRepository.saveAndFlush(collectible);

        // Get the collectible
        restCollectibleMockMvc.perform(get("/api/collectibles/{id}", collectible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(collectible.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.doubleValue()))
            .andExpect(jsonPath("$.cataloguenumber").value(DEFAULT_CATALOGUENUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCollectible() throws Exception {
        // Get the collectible
        restCollectibleMockMvc.perform(get("/api/collectibles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollectible() throws Exception {
        // Initialize the database
        collectibleRepository.saveAndFlush(collectible);

		int databaseSizeBeforeUpdate = collectibleRepository.findAll().size();

        // Update the collectible
        collectible.setName(UPDATED_NAME);
        collectible.setDescription(UPDATED_DESCRIPTION);
        collectible.setAge(UPDATED_AGE);
        collectible.setCataloguenumber(UPDATED_CATALOGUENUMBER);

        restCollectibleMockMvc.perform(put("/api/collectibles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(collectible)))
                .andExpect(status().isOk());

        // Validate the Collectible in the database
        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeUpdate);
        Collectible testCollectible = collectibles.get(collectibles.size() - 1);
        assertThat(testCollectible.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollectible.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollectible.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCollectible.getCataloguenumber()).isEqualTo(UPDATED_CATALOGUENUMBER);
    }

    @Test
    @Transactional
    public void deleteCollectible() throws Exception {
        // Initialize the database
        collectibleRepository.saveAndFlush(collectible);

		int databaseSizeBeforeDelete = collectibleRepository.findAll().size();

        // Get the collectible
        restCollectibleMockMvc.perform(delete("/api/collectibles/{id}", collectible.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Collectible> collectibles = collectibleRepository.findAll();
        assertThat(collectibles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
