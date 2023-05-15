package ru.vityaman.itmo.web.lab.taparia;

import org.junit.Test;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UserAccountTest {
    @Test
    public void testValidUser() {
        long id = 213;
        String name = "tester";
        byte[] hash = new byte[] {12, 23};
        byte[] salt = new byte[] {1, 2};

        UserAccount account = UserAccount.of(
            UserAccount.Id.of(id),
            UserAccount.Name.of(name),
            UserAccount.Password.fromHashAndSalt(hash, salt)
        );
        assertEquals(account.id().value(), id);
        assertEquals(account.name().value(), name);
        assertArrayEquals(account.password().hash(), hash);
        assertArrayEquals(account.password().salt(), salt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNameRestrictedSymbol() {
        UserAccount.Name name = UserAccount.Name.of("lala_la");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNameTooShort() {
        UserAccount.Name name = UserAccount.Name.of("la");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNameTooLong() {
        UserAccount.Name name = UserAccount.Name.of(
            Stream
                .iterate("1", s -> s)
                .limit(100)
                .collect(Collectors.joining(","))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIdNegative() {
        UserAccount.Id id = UserAccount.Id.of(-12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIdZero() {
        UserAccount.Id id = UserAccount.Id.of(0);
    }
}
