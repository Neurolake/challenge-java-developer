package br.com.neurotech.challenge.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ClientRequestDTOTest {
    @Test
    void shouldCreateAndAccessClientRequestDTO() {
        ClientRequestDTO dto = new ClientRequestDTO();
        dto.setName("Maria Silva");
        dto.setAge(30);
        dto.setIncome(10000.0);

        assertEquals("Maria Silva", dto.getName());
        assertEquals(30, dto.getAge());
        assertEquals(10000.0, dto.getIncome());
    }
}
