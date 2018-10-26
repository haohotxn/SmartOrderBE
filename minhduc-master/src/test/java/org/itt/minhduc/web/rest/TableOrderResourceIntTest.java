package org.itt.minhduc.web.rest;

import org.itt.minhduc.SmartorderApp;

import org.itt.minhduc.domain.TableOrder;
import org.itt.minhduc.repository.TableOrderRepository;
import org.itt.minhduc.service.TableOrderService;
import org.itt.minhduc.service.dto.TableOrderDTO;
import org.itt.minhduc.service.mapper.TableOrderMapper;
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

import org.itt.minhduc.domain.enumeration.StatusTable;
/**
 * Test class for the TableOrderResource REST controller.
 *
 * @see TableOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartorderApp.class)
public class TableOrderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final StatusTable DEFAULT_STATUS = StatusTable.EMPTY;
    private static final StatusTable UPDATED_STATUS = StatusTable.FULL;

    private static final String DEFAULT_ACCESS_TABLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_TABLE_CODE = "BBBBBBBBBB";

    @Autowired
    private TableOrderRepository tableOrderRepository;

    @Autowired
    private TableOrderMapper tableOrderMapper;
    
    @Autowired
    private TableOrderService tableOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTableOrderMockMvc;

    private TableOrder tableOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TableOrderResource tableOrderResource = new TableOrderResource(tableOrderService);
        this.restTableOrderMockMvc = MockMvcBuilders.standaloneSetup(tableOrderResource)
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
    public static TableOrder createEntity() {
        TableOrder tableOrder = new TableOrder()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .accessTableCode(DEFAULT_ACCESS_TABLE_CODE);
        return tableOrder;
    }

    @Before
    public void initTest() {
        tableOrderRepository.deleteAll();
        tableOrder = createEntity();
    }

    @Test
    public void createTableOrder() throws Exception {
        int databaseSizeBeforeCreate = tableOrderRepository.findAll().size();

        // Create the TableOrder
        TableOrderDTO tableOrderDTO = tableOrderMapper.toDto(tableOrder);
        restTableOrderMockMvc.perform(post("/api/table-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TableOrder in the database
        List<TableOrder> tableOrderList = tableOrderRepository.findAll();
        assertThat(tableOrderList).hasSize(databaseSizeBeforeCreate + 1);
        TableOrder testTableOrder = tableOrderList.get(tableOrderList.size() - 1);
        assertThat(testTableOrder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTableOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTableOrder.getAccessTableCode()).isEqualTo(DEFAULT_ACCESS_TABLE_CODE);
    }

    @Test
    public void createTableOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tableOrderRepository.findAll().size();

        // Create the TableOrder with an existing ID
        tableOrder.setId("existing_id");
        TableOrderDTO tableOrderDTO = tableOrderMapper.toDto(tableOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableOrderMockMvc.perform(post("/api/table-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TableOrder in the database
        List<TableOrder> tableOrderList = tableOrderRepository.findAll();
        assertThat(tableOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTableOrders() throws Exception {
        // Initialize the database
        tableOrderRepository.save(tableOrder);

        // Get all the tableOrderList
        restTableOrderMockMvc.perform(get("/api/table-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableOrder.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accessTableCode").value(hasItem(DEFAULT_ACCESS_TABLE_CODE.toString())));
    }
    
    @Test
    public void getTableOrder() throws Exception {
        // Initialize the database
        tableOrderRepository.save(tableOrder);

        // Get the tableOrder
        restTableOrderMockMvc.perform(get("/api/table-orders/{id}", tableOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tableOrder.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.accessTableCode").value(DEFAULT_ACCESS_TABLE_CODE.toString()));
    }

    @Test
    public void getNonExistingTableOrder() throws Exception {
        // Get the tableOrder
        restTableOrderMockMvc.perform(get("/api/table-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTableOrder() throws Exception {
        // Initialize the database
        tableOrderRepository.save(tableOrder);

        int databaseSizeBeforeUpdate = tableOrderRepository.findAll().size();

        // Update the tableOrder
        TableOrder updatedTableOrder = tableOrderRepository.findById(tableOrder.getId()).get();
        updatedTableOrder
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .accessTableCode(UPDATED_ACCESS_TABLE_CODE);
        TableOrderDTO tableOrderDTO = tableOrderMapper.toDto(updatedTableOrder);

        restTableOrderMockMvc.perform(put("/api/table-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableOrderDTO)))
            .andExpect(status().isOk());

        // Validate the TableOrder in the database
        List<TableOrder> tableOrderList = tableOrderRepository.findAll();
        assertThat(tableOrderList).hasSize(databaseSizeBeforeUpdate);
        TableOrder testTableOrder = tableOrderList.get(tableOrderList.size() - 1);
        assertThat(testTableOrder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTableOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTableOrder.getAccessTableCode()).isEqualTo(UPDATED_ACCESS_TABLE_CODE);
    }

    @Test
    public void updateNonExistingTableOrder() throws Exception {
        int databaseSizeBeforeUpdate = tableOrderRepository.findAll().size();

        // Create the TableOrder
        TableOrderDTO tableOrderDTO = tableOrderMapper.toDto(tableOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableOrderMockMvc.perform(put("/api/table-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TableOrder in the database
        List<TableOrder> tableOrderList = tableOrderRepository.findAll();
        assertThat(tableOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTableOrder() throws Exception {
        // Initialize the database
        tableOrderRepository.save(tableOrder);

        int databaseSizeBeforeDelete = tableOrderRepository.findAll().size();

        // Get the tableOrder
        restTableOrderMockMvc.perform(delete("/api/table-orders/{id}", tableOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TableOrder> tableOrderList = tableOrderRepository.findAll();
        assertThat(tableOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableOrder.class);
        TableOrder tableOrder1 = new TableOrder();
        tableOrder1.setId("id1");
        TableOrder tableOrder2 = new TableOrder();
        tableOrder2.setId(tableOrder1.getId());
        assertThat(tableOrder1).isEqualTo(tableOrder2);
        tableOrder2.setId("id2");
        assertThat(tableOrder1).isNotEqualTo(tableOrder2);
        tableOrder1.setId(null);
        assertThat(tableOrder1).isNotEqualTo(tableOrder2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableOrderDTO.class);
        TableOrderDTO tableOrderDTO1 = new TableOrderDTO();
        tableOrderDTO1.setId("id1");
        TableOrderDTO tableOrderDTO2 = new TableOrderDTO();
        assertThat(tableOrderDTO1).isNotEqualTo(tableOrderDTO2);
        tableOrderDTO2.setId(tableOrderDTO1.getId());
        assertThat(tableOrderDTO1).isEqualTo(tableOrderDTO2);
        tableOrderDTO2.setId("id2");
        assertThat(tableOrderDTO1).isNotEqualTo(tableOrderDTO2);
        tableOrderDTO1.setId(null);
        assertThat(tableOrderDTO1).isNotEqualTo(tableOrderDTO2);
    }
}
