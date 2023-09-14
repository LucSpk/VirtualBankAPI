package br.com.lucas.virtualBankAPI.services.users.impl;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.DataIntegrityViolationException;
import br.com.lucas.virtualBankAPI.services.exceptions.DivergentDataException;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServicesImplTest {

    @InjectMocks
    private UserServicesImpl services;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper modelMapper;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    public static final Integer ID = 1;
    public static final String NAME = "userTest";
    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "123456";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
        this.setModelMapper();
    }

    @Test
    public void whenFindByIdThenReturnAnUsuarioDTOInstance() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));

        UsuarioDTO response = services.findById(ID);

        assertNotNull(response);

        assertEquals(UsuarioDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    public void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage()));

        try {
            services.findById(ID);
            fail("Expected ObjectNotFoundException to be thrown");
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnNonEmptyList() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioDTO> response = services.findAll();
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());

        UsuarioDTO firstUser = response.get(0);
        assertEquals(ID, firstUser.getId());
        assertEquals(NAME, firstUser.getName());
        assertEquals(EMAIL, firstUser.getEmail());
        assertEquals(PASSWORD, firstUser.getPassword());
    }

    @Test
    public void whenFindAllThenReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<UsuarioDTO> response = services.findAll();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void whenCreateThenReturnUsuarioDTO() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(this.usuario);

        UsuarioDTO createdUser = services.create(usuario);

        assertNotNull(createdUser);
        assertEquals(ID, createdUser.getId());
        assertEquals(NAME, createdUser.getName());
        assertEquals(EMAIL, createdUser.getEmail());
        assertEquals(PASSWORD, createdUser.getPassword());
    }

    @Test
    public void whenCreateWithExistingEmailThenThrowDataIntegrityViolationException() {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));

        try {
            services.create(usuario);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(ErrorMessage.EAMIL_JA_CADASTRADO.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void whenUpdateThenReturnUpdatedUsuarioDTO() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(repository.save(usuario)).thenReturn(usuario);

        UsuarioDTO response = services.update(usuario, ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    public void whenUpdateWithDivergentDataThenThrowDivergentDataException() {
        try {
            assertThrows(DivergentDataException.class, () -> services.update(usuario, 2));
        } catch (Exception ex) {
            assertEquals(DivergentDataException.class, ex.getClass());
            assertEquals(ErrorMessage.DIVERGENCIA_NOS_DADOS.getMessage(), ex.getMessage());
        }

    }

    private void initializeVariables() {
        this.usuario = new Usuario(ID, NAME, EMAIL, PASSWORD);
        this.usuarioDTO = new UsuarioDTO(ID, NAME, EMAIL, PASSWORD);
    }

    private void setModelMapper() {
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);
        when(modelMapper.map(this.usuarioDTO, Usuario.class)).thenReturn(this.usuario);
    }
}