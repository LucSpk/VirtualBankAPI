package br.com.lucas.virtualBankAPI.services.userServices.impl;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private Optional<Usuario> optionalUsuario;

    public static final Integer ID = 1;
    public static final String NAME = "userTest";
    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "123456";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
    }

    @Test
    public void whenFindByIdThenReturnAnUsuarioDTOInstance() {
        when(repository.findById(anyInt())).thenReturn(this.optionalUsuario);
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

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
    void findAll() {
    }

    @Test
    void create() {
    }

    private void initializeVariables() {
        this.usuario = new Usuario(ID, NAME, EMAIL, PASSWORD);
        this.usuarioDTO = new UsuarioDTO(ID, NAME, EMAIL, PASSWORD);
        this.optionalUsuario = Optional.of(new Usuario(ID, NAME, EMAIL, PASSWORD));
    }

}