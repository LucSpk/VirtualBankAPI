package br.com.lucas.virtualBankAPI.controllers.users;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.services.users.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserServices userServices;

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
        initializeVariables();
        setModelMapper();
    }

    @Test
    public void whenFindByIdThenReturnAnUsuarioDTOInstance() {
        when(userServices.findById(anyInt())).thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> response = controller.findById(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UsuarioDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(ID, responseBody.getId());
        assertEquals(NAME, responseBody.getName());
        assertEquals(EMAIL, responseBody.getEmail());
        assertEquals(PASSWORD, responseBody.getPassword());

        verify(userServices, times(1)).findById(ID);
    }

    @Test
    public void whenFindAllThenReturnNonEmptyList() {
        when(userServices.findAll()).thenReturn(List.of(usuarioDTO));

        ResponseEntity<List<UsuarioDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<UsuarioDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(1, responseBody.size());

        UsuarioDTO firstUser = responseBody.get(0);
        assertEquals(ID, firstUser.getId());
        assertEquals(NAME, firstUser.getName());
        assertEquals(EMAIL, firstUser.getEmail());
        assertEquals(PASSWORD, firstUser.getPassword());

        verify(userServices, times(1)).findAll();
    }

    @Test
    public void whenFindAllThenReturnEmptyList() {
        when(userServices.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<UsuarioDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<UsuarioDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());

        verify(userServices, times(1)).findAll();
    }

    @Test
    public void whenCreateThenReturnUsuarioDTO() {
        when(userServices.create(any())).thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> response = controller.create(usuarioDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        URI expectedUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ID).toUri();
        assertEquals(expectedUri, response.getHeaders().getLocation());

        verify(userServices, times(1)).create(usuario);
    }

    @Test
    public void whenUpdateThenReturnSucess() {
        when(userServices.update(any(), anyInt())).thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> response = controller.update(usuarioDTO, ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsuarioDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());

    }

    private void initializeVariables() {
        usuario = new Usuario(ID, NAME, EMAIL, PASSWORD);
        usuarioDTO = new UsuarioDTO(ID, NAME, EMAIL, PASSWORD);
    }

    private void setModelMapper() {
        when(modelMapper.map(usuarioDTO, Usuario.class)).thenReturn(usuario);
    }
}