import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for Matrix class.
 */
public class MatrixTest {

    public MatrixTest() {
        Locale.setDefault(Locale.US);
    }

    private static final String globalFile = "src/test/resources/matrix.txt";
    private static final String symmetricFile = "src/test/resources/matrix-symmetric.txt";
    private static final double threshold = 0.001d;

    private Matrix readMatrix(Scanner scanner) {
        var lines = scanner.nextInt();
        var columns = scanner.nextInt();
        var array = new double[lines][columns];

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = scanner.nextDouble();
            }
        }

        return new Matrix(array);
    }

    private boolean areEqual(Matrix a, Matrix b) {
        if (a.lines() != b.lines() || a.columns() != b.columns()) {
            return false;
        }

        for (int i = 0; i < a.lines(); i++) {
            for (int j = 0; j < a.columns(); j++) {
                if (Math.abs(a.get(i, j) - b.get(i, j)) > threshold) {
                    return false;
                }
            }
        }

        return true;
    }

    private String toString(Matrix m) {
        var builder = new StringBuilder();

        for (var i = 0; i < m.lines(); i++) {
            for (var j = 0; j < m.columns(); j++) {
                builder.append(String.format("%10.6f", m.get(i, j)));
            }
            builder.append("\n");
        }

        return builder.toString();
    }


    @Test
    public void testGlobal() {
        try (var scanner = new Scanner(new File(globalFile))) {
            while (scanner.hasNext()) {
                var caseName = scanner.next();
                var a = readMatrix(scanner);
                var b = readMatrix(scanner);
                var c = readMatrix(scanner);
                var d = readMatrix(scanner);
                var ta = readMatrix(scanner);
                var tb = readMatrix(scanner);

                var aaPlusB = readMatrix(scanner);
                var bbPlusA = readMatrix(scanner);
                var aaMinusB = readMatrix(scanner);
                var bbMinusA = readMatrix(scanner);
                var scalar = scanner.nextDouble();
                var aaTimesScalar = readMatrix(scanner);
                var bbTimesScalar = readMatrix(scanner);

                var aaTimesC = readMatrix(scanner);

                var aaIsSquare = scanner.nextBoolean();
                var bbIsSquare = scanner.nextBoolean();
                var aaIsSymmetric = scanner.nextBoolean();
                var bbIsSymmetric = scanner.nextBoolean();

                var ddIsSquare = scanner.nextBoolean();
                var ddIsSymmetric = scanner.nextBoolean();

                // transpose
                Assertions.assertTrue(areEqual(ta, a.transpose()),
                    String.format("%s. Wrong transpose result. "
                        + "Matrix: \n%s. "
                        + "Transpose (result): \n%s\n"
                        + "Transpose (expected): \n%s\n",
                        caseName,
                        toString(a),
                        toString(a.transpose()),
                        toString(ta)));
                Assertions.assertTrue(areEqual(tb, b.transpose()),
                    String.format("%s. Wrong transpose result. "
                        + "Matrix: \n%s. "
                        + "Transpose: \n%s\n"
                        + "Transpose (expected): \n%s\n",
                        caseName,
                        toString(b),
                        toString(b.transpose()),
                        toString(tb)));

                // binary operators
                Assertions.assertTrue(areEqual(aaPlusB, a.plus(b)),
                    String.format("%s. Wrong addition result. \n"
                            + "Matrix A: \n%s. "
                            + "Matrix B: \n%s. "
                            + "A + B (expected): \n%s\n"
                            + "A + B (result): \n%s\n",
                        caseName, toString(a), toString(b), toString(aaPlusB),
                        toString(a.plus(b))));
                Assertions.assertTrue(areEqual(bbPlusA, b.plus(a)),
                    String.format("%s. Wrong addition result. \n"
                            + "Matrix B: \n%s. "
                            + "Matrix A: \n%s. "
                            + "B + A (expected): \n%s\n"
                            + "B + A (result): \n%s\n",
                        caseName, toString(b), toString(a), toString(bbPlusA),
                        toString(b.plus(a))));
                Assertions.assertTrue(areEqual(aaMinusB, a.minus(b)),
                    String.format("%s. Wrong subtraction result. \n"
                            + "Matrix A: \n%s. "
                            + "Matrix B: \n%s. "
                            + "A - B (expected): \n%s\n"
                            + "A - B (result): \n%s\n",
                        caseName, toString(a), toString(b), toString(aaMinusB),
                        toString(a.minus(b))));
                Assertions.assertTrue(areEqual(bbMinusA, b.minus(a)),
                    String.format("%s. Wrong subtraction result. \n"
                            + "Matrix B: \n%s. "
                            + "Matrix A: \n%s. "
                            + "B - A (expected): \n%s\n"
                            + "B - A (result): \n%s\n",
                        caseName, toString(b), toString(a), toString(bbMinusA),
                        toString(b.minus(a))));
                Assertions.assertTrue(areEqual(aaTimesScalar, a.times(scalar)),
                    String.format("%s. Wrong multiplication by scalar result. \n"
                            + "Matrix: \n%s. "
                            + "Scalar: \n%.6f. "
                            + "Result: \n%s\n"
                            + "Expected: \n%s\n",
                        caseName,
                        toString(a),
                        scalar,
                        toString(a.times(scalar)),
                        toString(aaTimesScalar)));
                Assertions.assertTrue(areEqual(bbTimesScalar, b.times(scalar)),
                    String.format("%s. Wrong multiplication by scalar result. "
                            + "Matrix: \n%s. "
                            + "Scalar: \n%.6f. "
                            + "Result: \n%s\n"
                            + "Expected: \n%s\n",
                        caseName,
                        toString(b),
                        scalar,
                        toString(b.times(scalar)),
                        toString(bbTimesScalar)));
                Assertions.assertTrue(areEqual(aaTimesC, a.times(c)),
                    String.format("%s. Wrong multiplication result. "
                            + "Matrix A: \n%s. "
                            + "Matrix C: \n%s. "
                            + "A * C (Result): \n%s\n"
                            + "A * C (expected): \n%s\n",
                        caseName,
                        toString(a),
                        toString(c),
                        toString(a.times(c)),
                        toString(aaTimesC)));

                // predicates
                Assertions.assertEquals(aaIsSquare, a.isSquare(),
                    String.format("%s. Wrong isSquare result. "
                        + "Matrix: \n%s. "
                        + "Expected: \n%s. "
                        + "Result: \n%s\n", caseName, toString(a), aaIsSquare, a.isSquare()));
                Assertions.assertEquals(bbIsSquare, b.isSquare(),
                    String.format("%s. Wrong isSquare result. "
                        + "Matrix: \n%s. "
                        + "Expected: \n%s. "
                        + "Result: \n%s\n", caseName, toString(b), bbIsSquare, b.isSquare()));
                Assertions.assertEquals(aaIsSymmetric, a.isSymmetric(),
                    String.format("%s. Wrong isSymmetric result. "
                        + "Matrix: \n%s. "
                        + "Expected: \n%s. "
                        + "Result: \n%s\n", caseName, toString(a), aaIsSymmetric, a.isSymmetric()));
                Assertions.assertEquals(bbIsSymmetric, b.isSymmetric(),
                    String.format("%s. Wrong isSymmetric result. "
                        + "Matrix: \n%s. "
                        + "Expected: \n%s. "
                        + "Result: \n%s\n", caseName, toString(b), bbIsSymmetric, b.isSymmetric()));
                Assertions.assertEquals(ddIsSquare, d.isSquare(),
                    String.format("%s. Wrong isSquare result. "
                        + "Matrix: \n%s. "
                        + "Expected: \n%s. "
                        + "Result: \n%s\n", caseName, toString(d), ddIsSymmetric, d.isSymmetric()));
                Assertions.assertEquals(ddIsSymmetric, d.isSymmetric(),
                    String.format("%s. Wrong isSymmetric result. "
                        + "Matrix: \n%s. "
                        + "Expected: \n%s. "
                        + "Result: \n%s\n", caseName, toString(d), ddIsSymmetric, d.isSymmetric()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Failed to parse test cases file");
        }
    }

    @Test
    public void testSymmetric() {
        try (var scanner = new Scanner(new File(symmetricFile))) {
            var m = readMatrix(scanner);

            Assertions.assertTrue(m.isSymmetric(), "Matrix is symmetric. \n"
                + "Matrix: \n"
                + m);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Failed to parse test cases file");
        }
    }

}
