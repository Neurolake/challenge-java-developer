package br.com.neurotech.challenge.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ClientResponseDTOTest {
    @Test
    void shouldCreateAndAccessClientResponseDTO() {
        ClientResponseDTO dto = new ClientResponseDTO("João Silva", 42, 8500.0);

        assertEquals("João Silva", dto.getName());
        assertEquals(42, dto.getAge());
        assertEquals(8500.0, dto.getIncome());
    }
}
