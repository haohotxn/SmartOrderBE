package org.itt.minhduc.web.rest;

import org.itt.minhduc.SmartorderApp;

import org.itt.minhduc.domain.CommonEntity;
import org.itt.minhduc.repository.CommonEntityRepository;
import org.itt.minhduc.web.rest.errors.ExceptionTranslator;

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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static org.itt.minhduc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommonEntityResource REST controller.
 *
 * @see CommonEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartorderApp.class)
public class CommonEntityResourceIntTest {

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommonEntityRepository commonEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCommonEntityMockMvc;

    private CommonEntity commonEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommonEntityResource commonEntityResource = new CommonEntityResource(commonEntityRepository);
        this.restCommonEntityMockMvc = MockMvcBuilders.standaloneSetup(commonEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonEntity createEntity() {
        CommonEntity commonEntity = new CommonEntity()
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return commonEntity;
    }

    @Before
    public void initTest() {
        commonEntityRepository.deleteAll();
        commonEntity = createEntity();
    }

    @Test
    public void createCommonEntity() throws Exception {
        int databaseSizeBeforeCreate = commonEntityRepository.findAll().size();

        // Create the CommonEntity
        restCommonEntityMockMvc.perform(post("/api/common-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonEntity)))
            .andExpect(status().isCreated());

        // Validate the CommonEntity in the database
        List<CommonEntity> commonEntityList = commonEntityRepository.findAll();
        assertThat(commonEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CommonEntity testCommonEntity = commonEntityList.get(commonEntityList.size() - 1);
        assertThat(testCommonEntity.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCommonEntity.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    public void createCommonEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commonEntityRepository.findAll().size();

        // Create the CommonEntity with an existing ID
        commonEntity.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonEntityMockMvc.perform(post("/api/common-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CommonEntity in the database
        List<CommonEntity> commonEntityList = commonEntityRepository.findAll();
        assertThat(commonEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCommonEntities() throws Exception {
        // Initialize the database
        commonEntityRepository.save(commonEntity);

        // Get all the commonEntityList
        restCommonEntityMockMvc.perform(get("/api/common-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonEntity.getId())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    public void getCommonEntity() throws Exception {
        // Initialize the database
        commonEntityRepository.save(commonEntity);

        // Get the commonEntity
        restCommonEntityMockMvc.perform(get("/api/common-entities/{id}", commonEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commonEntity.getId()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    public void getNonExistingCommonEntity() throws Exception {
        // Get the commonEntity
        restCommonEntityMockMvc.perform(get("/api/common-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommonEntity() throws Exception {
        // Initialize the database
        commonEntityRepository.save(commonEntity);

        int databaseSizeBeforeUpdate = commonEntityRepository.findAll().size();

        // Update the commonEntity
        CommonEntity updatedCommonEntity = commonEntityRepository.findById(commonEntity.getId()).get();
        updatedCommonEntity
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restCommonEntityMockMvc.perform(put("/api/common-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommonEntity)))
            .andExpect(status().isOk());

        // Validate the CommonEntity in the database
        List<CommonEntity> commonEntityList = commonEntityRepository.findAll();
        assertThat(commonEntityList).hasSize(databaseSizeBeforeUpdate);
        CommonEntity testCommonEntity = commonEntityList.get(commonEntityList.size() - 1);
        assertThat(testCommonEntity.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCommonEntity.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    public void updateNonExistingCommonEntity() throws Exception {
        int databaseSizeBeforeUpdate = commonEntityRepository.findAll().size();

        // Create the CommonEntity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonEntityMockMvc.perform(put("/api/common-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CommonEntity in the database
        List<CommonEntity> commonEntityList = commonEntityRepository.findAll();
        assertThat(commonEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCommonEntity() throws Exception {
        // Initialize the database
        commonEntityRepository.save(commonEntity);

        int databaseSizeBeforeDelete = commonEntityRepository.findAll().size();

        // Get the commonEntity
        restCommonEntityMockMvc.perform(delete("/api/common-entities/{id}", commonEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommonEntity> commonEntityList = commonEntityRepository.findAll();
        assertThat(commonEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonEntity.class);
        CommonEntity commonEntity1 = new CommonEntity();
        commonEntity1.setId("id1");
        CommonEntity commonEntity2 = new CommonEntity();
        commonEntity2.setId(commonEntity1.getId());
        assertThat(commonEntity1).isEqualTo(commonEntity2);
        commonEntity2.setId("id2");
        assertThat(commonEntity1).isNotEqualTo(commonEntity2);
        commonEntity1.setId(null);
        assertThat(commonEntity1).isNotEqualTo(commonEntity2);
    }
}
