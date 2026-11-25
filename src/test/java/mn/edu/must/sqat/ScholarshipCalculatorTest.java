package mn.edu.must.sqat;

// ЛАБ 11 — B2: B1-ийн яг утгын шалгалтууд + ЗААГ УТГЫН тестүүд.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    private static final double EPS = 1e-9;

    ScholarshipCalculator calc;

    @BeforeEach
    void setUp() { calc = new ScholarshipCalculator(); }

    // ---------------- letterGrade ----------------

    @Test
    void letterGrade_returnsExactGradeForAllRanges() {
        assertEquals("A", calc.letterGrade(95));
        assertEquals("B", calc.letterGrade(85));
        assertEquals("C", calc.letterGrade(75));
        assertEquals("D", calc.letterGrade(65));
        assertEquals("F", calc.letterGrade(30));
    }

    @Test
    void letterGrade_boundary_A_B() {
        assertEquals("A", calc.letterGrade(90));
        assertEquals("B", calc.letterGrade(89.99));
    }

    @Test
    void letterGrade_boundary_B_C() {
        assertEquals("B", calc.letterGrade(80));
        assertEquals("C", calc.letterGrade(79.99));
    }

    @Test
    void letterGrade_boundary_C_D() {
        assertEquals("C", calc.letterGrade(70));
        assertEquals("D", calc.letterGrade(69.99));
    }

    @Test
    void letterGrade_boundary_D_F() {
        assertEquals("D", calc.letterGrade(60));
        assertEquals("F", calc.letterGrade(59.99));
    }

    @Test
    void letterGrade_inputLimits_0_and_100_areValid() {
        assertEquals("F", calc.letterGrade(0));
        assertEquals("A", calc.letterGrade(100));
    }

    @Test
    void letterGrade_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-0.01));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(100.01));
    }

    // ---------------- gpa ----------------

    @Test
    void gpa_returnsExactAverage() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), EPS); // (4+3)/2
        assertEquals(1.5, calc.gpa(new double[]{75, 65}), EPS); // (2+1)/2
        assertEquals(0.0, calc.gpa(new double[]{40}), EPS);     // F = 0
    }

    @Test
    void gpa_eachLetterGrade_singleScore() {
        assertEquals(4.0, calc.gpa(new double[]{95}), EPS); // A
        assertEquals(3.0, calc.gpa(new double[]{85}), EPS); // B
        assertEquals(2.0, calc.gpa(new double[]{75}), EPS); // C
        assertEquals(1.0, calc.gpa(new double[]{65}), EPS); // D
        assertEquals(0.0, calc.gpa(new double[]{50}), EPS); // F (default)
    }

    @Test
    void gpa_allGradesTogether() {
        // (4 + 3 + 2 + 1 + 0) / 5 = 2.0
        assertEquals(2.0, calc.gpa(new double[]{90, 80, 70, 60, 59.99}), EPS);
    }

    @Test
    void gpa_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{90, 101}));
    }

    // ---------------- scholarship ----------------

    @Test
    void scholarship_returnsExactPercent() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
        assertEquals(70,  calc.scholarship(3.5, 13, false));
        assertEquals(50,  calc.scholarship(3.1, 5, false));
        assertEquals(0,   calc.scholarship(2.0, 1, false));
        assertEquals(0,   calc.scholarship(4.0, 20, true));
    }

    @Test
    void scholarship_violation_alwaysZero() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
        assertEquals(0, calc.scholarship(3.5, 13, true));
        assertEquals(0, calc.scholarship(3.1, 5, true));
    }

    @Test
    void scholarship_boundary_100() {
        assertEquals(100, calc.scholarship(3.8, 15, false));
        assertEquals(70,  calc.scholarship(3.79, 15, false));
        assertEquals(70,  calc.scholarship(3.8, 14, false));
    }

    @Test
    void scholarship_boundary_70() {
        assertEquals(70, calc.scholarship(3.4, 12, false));
        assertEquals(50, calc.scholarship(3.39, 12, false));
        assertEquals(50, calc.scholarship(3.4, 11, false));
    }

    @Test
    void scholarship_boundary_50() {
        assertEquals(50, calc.scholarship(3.0, 0, false));
        assertEquals(0,  calc.scholarship(2.99, 20, false));
    }

    @Test
    void scholarship_inputLimits_areValid() {
        assertEquals(0,   calc.scholarship(0.0, 10, false));
        assertEquals(100, calc.scholarship(4.0, 15, false));
        assertEquals(50,  calc.scholarship(3.5, 0, false));
    }

    @Test
    void scholarship_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.0, -1, false));
    }
}
