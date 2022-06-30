import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for Rational.
 */
public class RationalTest {
    private static final String rationalTestCases = "src/test/resources/rationals.txt";

    public RationalTest() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void testRationals() {
        try (var scanner = new Scanner(new File(rationalTestCases))) {
            while (scanner.hasNext()) {
                var r1 = new Rational(scanner.nextInt(), scanner.nextInt());
                var r2 = new Rational(scanner.nextInt(), scanner.nextInt());
                var add12 = new Rational(scanner.nextInt(), scanner.nextInt());
                var add21 = new Rational(scanner.nextInt(), scanner.nextInt());
                var sub12 = new Rational(scanner.nextInt(), scanner.nextInt());
                var sub21 = new Rational(scanner.nextInt(), scanner.nextInt());
                var mult12 = new Rational(scanner.nextInt(), scanner.nextInt());
                var mult21 = new Rational(scanner.nextInt(), scanner.nextInt());
                var div12 = new Rational(scanner.nextInt(), scanner.nextInt());
                var div21 = new Rational(scanner.nextInt(), scanner.nextInt());
                var eq12 = scanner.nextBoolean();
                var eq21 = scanner.nextBoolean();

                Assertions.assertEquals(add12, r1.plus(r2),
                    String.format("(%s) + (%s) lead to wrong result.", r1, r2));
                Assertions.assertEquals(add21, r2.plus(r1),
                    String.format("(%s) + (%s) lead to wrong result.", r2, r1));
                Assertions.assertEquals(sub12, r1.minus(r2),
                    String.format("(%s) - (%s) lead to wrong result.", r1, r2));
                Assertions.assertEquals(sub21, r2.minus(r1),
                    String.format("(%s) - (%s) lead to wrong result.", r2, r1));
                Assertions.assertEquals(mult12, r1.times(r2),
                    String.format("(%s) * (%s) lead to wrong result.", r1, r2));
                Assertions.assertEquals(mult21, r2.times(r1),
                    String.format("(%s) * (%s) lead to wrong result.", r2, r1));
                Assertions.assertEquals(div12, r1.divides(r2),
                    String.format("(%s) / (%s) lead to wrong result.", r1, r2));
                Assertions.assertEquals(div21, r2.divides(r1),
                    String.format("(%s) / (%s) lead to wrong result.", r2, r1));
                Assertions.assertEquals(eq12, r1.isEqual(r2),
                    String.format("(%s) == (%s) lead to wrong result.", r1, r2));
                Assertions.assertEquals(eq21, r2.isEqual(r1),
                    String.format("(%s) == (%s) lead to wrong result.", r2, r1));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open rationals test case file");
            e.printStackTrace();
        }
    }
}
