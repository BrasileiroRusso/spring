package ru.geekbrains.vklimovich.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.geekbrains.vklimovich.springmvc.SpringMvcApplicationTest;
import ru.geekbrains.vklimovich.springmvc.dto.CategoryDto;
import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;
import ru.geekbrains.vklimovich.springmvc.service.ProductService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

class ProductControllerTest extends SpringMvcApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objMapper;

    @MockBean
    private ProductService productService;

    private static List<ProductDto> products;

    @BeforeEach
    void setUp() {
        products = Collections.singletonList(new ProductDto(1L, "product1", BigDecimal.valueOf(500.00), new CategoryDto(1L, "OTHER"), ""));

        Mockito.when(productService.getProductCatalog("", null, null, ""))
               .thenReturn(products);
    }

    @Test
    void getProductList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("catalog"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products));
    }

    @Test
    void getNewProductForm() throws Exception {
        MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post("/new");
        ProductDto newProduct = new ProductDto(10L, "new_product", BigDecimal.valueOf(50000), new CategoryDto(1L, "OTHER"), "");

        rb.param("id", Long.toString(newProduct.getId()));
        rb.param("title", newProduct.getTitle());
        rb.param("cost", newProduct.getCost().toString());
        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("confirm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"));
    }

    @Test
    void addProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add_product"));
    }
}