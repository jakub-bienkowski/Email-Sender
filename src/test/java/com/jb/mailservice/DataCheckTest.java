package com.jb.mailservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataCheckTest {

    @Test
    void isEmailCorrect() {

        Assertions.assertTrue(DataCheck.isEmailCorrect("bien@wp.pl"));
        Assertions.assertFalse(DataCheck.isEmailCorrect(null));
        Assertions.assertFalse(DataCheck.isEmailCorrect("ja"));
        Assertions.assertFalse(DataCheck.isEmailCorrect("jak@"));
        Assertions.assertFalse(DataCheck.isEmailCorrect("jaja.aaa"));

    }
}