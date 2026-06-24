package com.awbd.gamerent;

import com.awbd.gamerent.model.Category;
import com.awbd.gamerent.model.Console;
import com.awbd.gamerent.repository.CategoryRepository;
import com.awbd.gamerent.repository.ConsoleRepository;
import com.awbd.gamerent.service.CategoryService;
import com.awbd.gamerent.service.ConsoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OtherServicesUnitTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private ConsoleRepository consoleRepository;
    @InjectMocks
    private ConsoleService consoleService;

    @Test
    void testCategoryService() {
        Category cat = new Category("Acțiune");

        when(categoryRepository.findAll()).thenReturn(List.of(cat));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(categoryRepository.save(any())).thenReturn(cat);

        assertEquals(1, categoryService.findAllCategories().size());
        assertNotNull(categoryService.findCategoryById(1L));
        assertNotNull(categoryService.saveCategory(cat));

        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testConsoleService() {
        Console con = new Console();
        con.setName("Xbox");

        when(consoleRepository.findAll()).thenReturn(List.of(con));
        when(consoleRepository.findById(1L)).thenReturn(Optional.of(con));
        when(consoleRepository.save(any())).thenReturn(con);

        assertEquals(1, consoleService.findAllConsoles().size());
        assertNotNull(consoleService.findConsoleById(1L));
        assertNotNull(consoleService.saveConsole(con));

        consoleService.deleteConsole(1L);
        verify(consoleRepository, times(1)).deleteById(1L);
    }
}