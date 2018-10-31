package org.itt.minhduc.web.rest;

import org.itt.minhduc.SmartorderApp;

import org.itt.minhduc.domain.UserAccess;
import org.itt.minhduc.repository.UserAccessRepository;
import org.itt.minhduc.service.UserAccessService;
import org.itt.minhduc.service.dto.UserAccessDTO;
import org.itt.minhduc.service.mapper.UserAccessMapper;
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

import java.util.List;


import static org.itt.minhduc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.itt.minhduc.domain.enumeration.Role;
/**
 * Test class for the UserAccessResource REST controller.
 *
 * @see UserAccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartorderApp.class)
public class UserAccessResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASS_WORD = "AAAAAAAAAA";
    private static final String UPDATED_PASS_WORD = "BBBBBBBBBB";

    private static final Role DEFAULT_ROLE = Role.ADMIN;
    private static final Role UPDATED_ROLE = Role.EMPLOYEE;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private UserAccessMapper userAccessMapper;
    
    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUserAccessMockMvc;

    private UserAccess userAccess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAccessResource userAccessResource = new UserAccessResource(userAccessService);
        this.restUserAccessMockMvc = MockMvcBuilders.standaloneSetup(userAccessResource)
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
    public static UserAccess createEntity() {
        UserAccess userAccess = new UserAccess()
            .userName(DEFAULT_USER_NAME)
            .passWord(DEFAULT_PASS_WORD)
            .role(DEFAULT_ROLE);
        return userAccess;
    }

    @Before
    public void initTest() {
        userAccessRepository.deleteAll();
        userAccess = createEntity();
    }

    @Test
    public void createUserAccess() throws Exception {
        int databaseSizeBeforeCreate = userAccessRepository.findAll().size();

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);
        restUserAccessMockMvc.perform(post("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserAccess.getPassWord()).isEqualTo(DEFAULT_PASS_WORD);
        assertThat(testUserAccess.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    public void createUserAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccessRepository.findAll().size();

        // Create the UserAccess with an existing ID
        userAccess.setId("existing_id");
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccessMockMvc.perform(post("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUserAccesses() throws Exception {
        // Initialize the database
        userAccessRepository.save(userAccess);

        // Get all the userAccessList
        restUserAccessMockMvc.perform(get("/api/user-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccess.getId())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].passWord").value(hasItem(DEFAULT_PASS_WORD.toString())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    public void getUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.save(userAccess);

        // Get the userAccess
        restUserAccessMockMvc.perform(get("/api/user-accesses/{id}", userAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAccess.getId()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.passWord").value(DEFAULT_PASS_WORD.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    public void getNonExistingUserAccess() throws Exception {
        // Get the userAccess
        restUserAccessMockMvc.perform(get("/api/user-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.save(userAccess);

        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Update the userAccess
        UserAccess updatedUserAccess = userAccessRepository.findById(userAccess.getId()).get();
        updatedUserAccess
            .userName(UPDATED_USER_NAME)
            .passWord(UPDATED_PASS_WORD)
            .role(UPDATED_ROLE);
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(updatedUserAccess);

        restUserAccessMockMvc.perform(put("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isOk());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserAccess.getPassWord()).isEqualTo(UPDATED_PASS_WORD);
        assertThat(testUserAccess.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    public void updateNonExistingUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccessMockMvc.perform(put("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.save(userAccess);

        int databaseSizeBeforeDelete = userAccessRepository.findAll().size();

        // Get the userAccess
        restUserAccessMockMvc.perform(delete("/api/user-accesses/{id}", userAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccess.class);
        UserAccess userAccess1 = new UserAccess();
        userAccess1.setId("id1");
        UserAccess userAccess2 = new UserAccess();
        userAccess2.setId(userAccess1.getId());
        assertThat(userAccess1).isEqualTo(userAccess2);
        userAccess2.setId("id2");
        assertThat(userAccess1).isNotEqualTo(userAccess2);
        userAccess1.setId(null);
        assertThat(userAccess1).isNotEqualTo(userAccess2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccessDTO.class);
        UserAccessDTO userAccessDTO1 = new UserAccessDTO();
        userAccessDTO1.setId("id1");
        UserAccessDTO userAccessDTO2 = new UserAccessDTO();
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
        userAccessDTO2.setId(userAccessDTO1.getId());
        assertThat(userAccessDTO1).isEqualTo(userAccessDTO2);
        userAccessDTO2.setId("id2");
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
        userAccessDTO1.setId(null);
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
    }
}
