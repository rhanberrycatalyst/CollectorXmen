package com.collectorthrd.web.rest;

import com.collectorthrd.Application;
import com.collectorthrd.domain.Forsale;
import com.collectorthrd.repository.ForsaleRepository;
import com.collectorthrd.service.ForsaleService;

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
 * Test class for the ForsaleResource REST controller.
 *
 * @see ForsaleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ForsaleResourceIntTest {


    private static final Boolean DEFAULT_FORSALE = false;
    private static final Boolean UPDATED_FORSALE = true;

    @Inject
    private ForsaleRepository forsaleRepository;

    @Inject
    private ForsaleService forsaleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restForsaleMockMvc;

    private Forsale forsale;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ForsaleResource forsaleResource = new ForsaleResource();
        ReflectionTestUtils.setField(forsaleResource, "forsaleService", forsaleService);
        this.restForsaleMockMvc = MockMvcBuilders.standaloneSetup(forsaleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        forsale = new Forsale();
        forsale.setForsale(DEFAULT_FORSALE);
    }

    @Test
    @Transactional
    public void createForsale() throws Exception {
        int databaseSizeBeforeCreate = forsaleRepository.findAll().size();

        // Create the Forsale

        restForsaleMockMvc.perform(post("/api/forsales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(forsale)))
                .andExpect(status().isCreated());

        // Validate the Forsale in the database
        List<Forsale> forsales = forsaleRepository.findAll();
        assertThat(forsales).hasSize(databaseSizeBeforeCreate + 1);
        Forsale testForsale = forsales.get(forsales.size() - 1);
        assertThat(testForsale.getForsale()).isEqualTo(DEFAULT_FORSALE);
    }

    @Test
    @Transactional
    public void checkForsaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = forsaleRepository.findAll().size();
        // set the field null
        forsale.setForsale(null);

        // Create the Forsale, which fails.

        restForsaleMockMvc.perform(post("/api/forsales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(forsale)))
                .andExpect(status().isBadRequest());

        List<Forsale> forsales = forsaleRepository.findAll();
        assertThat(forsales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForsales() throws Exception {
        // Initialize the database
        forsaleRepository.saveAndFlush(forsale);

        // Get all the forsales
        restForsaleMockMvc.perform(get("/api/forsales?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(forsale.getId().intValue())))
                .andExpect(jsonPath("$.[*].forsale").value(hasItem(DEFAULT_FORSALE.booleanValue())));
    }

    @Test
    @Transactional
    public void getForsale() throws Exception {
        // Initialize the database
        forsaleRepository.saveAndFlush(forsale);

        // Get the forsale
        restForsaleMockMvc.perform(get("/api/forsales/{id}", forsale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(forsale.getId().intValue()))
            .andExpect(jsonPath("$.forsale").value(DEFAULT_FORSALE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingForsale() throws Exception {
        // Get the forsale
        restForsaleMockMvc.perform(get("/api/forsales/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForsale() throws Exception {
        // Initialize the database
        forsaleRepository.saveAndFlush(forsale);

		int databaseSizeBeforeUpdate = forsaleRepository.findAll().size();

        // Update the forsale
        forsale.setForsale(UPDATED_FORSALE);

        restForsaleMockMvc.perform(put("/api/forsales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(forsale)))
                .andExpect(status().isOk());

        // Validate the Forsale in the database
        List<Forsale> forsales = forsaleRepository.findAll();
        assertThat(forsales).hasSize(databaseSizeBeforeUpdate);
        Forsale testForsale = forsales.get(forsales.size() - 1);
        assertThat(testForsale.getForsale()).isEqualTo(UPDATED_FORSALE);
    }

    @Test
    @Transactional
    public void deleteForsale() throws Exception {
        // Initialize the database
        forsaleRepository.saveAndFlush(forsale);

		int databaseSizeBeforeDelete = forsaleRepository.findAll().size();

        // Get the forsale
        restForsaleMockMvc.perform(delete("/api/forsales/{id}", forsale.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Forsale> forsales = forsaleRepository.findAll();
        assertThat(forsales).hasSize(databaseSizeBeforeDelete - 1);
    }
}
