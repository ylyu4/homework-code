package tw.training.homework.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @Test
    void should_generate_token_successfully() {
        // given
        Long userId = 1L;
        String username = "thisIsUsername";

        //then
        assert JwtUtils.generateToken(userId, username).contains("Bearer");
    }

}