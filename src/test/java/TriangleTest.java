import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import triangle.Triangle;

public class TriangleTest {

    //1a
    @DataProvider
    public static Object[][] objectsForCheckSidesAboveZero() {
        return new Object[][]{{22, 0, 22.6}, {-1, 2, 6}, {4.8d, 12, -2}};
    }

    @Test(dataProvider = "objectsForCheckSidesAboveZero")
    public void testSideAboveZero(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(triangle.checkTriangle());
        softAssert.assertTrue(triangle.getMessage().contains("<=0"));
        softAssert.assertAll();
    }
    //программа не корректно работает при передаче 0 или отриц. числа в третий аргумен:
    // треугольник прроверен без ошибок  и сообщение с ошибкой не присваивается


    //1b
    @DataProvider
    public static Object[][] objectsForCheckAspectRatio() {
        return new Object[][]{{8, 22.3, 20}, {12.6, 9, 18}, {15, 16, 11}};
    }

    @Test(dataProvider = "objectsForCheckAspectRatio")
    public void testAspectRatio(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        Assert.assertTrue(triangle.checkTriangle());
    }
    //программа корректно работает при передаче корректных значений в аргументы

    //1b-1
    @DataProvider
    public static Object[][] objectsForCheckAspectRatioIncorrectValue() {
        return new Object[][]{{1, 22.3, 20}, {12.6, 2, 18}, {15, 16, 1}};
    }

    @Test(dataProvider = "objectsForCheckAspectRatioIncorrectValue")
    public void testAspectRatioIncorrectValue(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(triangle.checkTriangle());
        softAssert.assertTrue(triangle.getMessage().contains("+"));
        softAssert.assertAll();
        ;
    }
    //программа корректно обрабатывает недопустимые значения сторон треугольника,
    // когда сумма 2 сторон не больше 3-й стороны

    //1c
    @Test
    public void testOverflowDouble() {
        Triangle triangle = new Triangle(Double.MAX_VALUE + 1, Double.MAX_VALUE + 1, Double.MAX_VALUE + 1);
        System.out.println(Double.MAX_VALUE);
        Assert.assertTrue(triangle.checkTriangle());
    }
    //при выходе за пределы значение double программа работает корректно

    // 1c-1
    @Test
    public void testOverflowInt() {
        Triangle triangle = new Triangle(Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1);
        Assert.assertTrue(triangle.checkTriangle());
    }
    //при выходе за пределы значение int программа работает не корректно

    // 2a
    @Test
    public void testEquilateral() {
        Triangle triangle = new Triangle(8, 8, 8);
        Assert.assertEquals(triangle.detectTriangle(), 1);
    }
    //Программа не определяет что треугольник равносторонний

    //2b
    @DataProvider
    public static Object[][] objectsForCheckIsosceles() {
        return new Object[][]{{22, 22, 11}, {11, 22, 22}, {22, 11, 22},
                {155, 14, 155}, {14, 155, 155}, {155, 155, 14}};
    }

    @Test(dataProvider = "objectsForCheckIsosceles")
    public void testIsosceles(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        Assert.assertEquals(triangle.detectTriangle(), 2);
    }
    //программа корретно определяет равнобедренные треугольники

    //2c
    @DataProvider
    public static Object[][] objectsForCheckRectangular() {
        return new Object[][]{{3, 4, 5}, {3, 5, 4}, {4, 3, 5}, {4, 5, 3},
                {5, 3, 4}, {5, 4, 3}, {5, Math.sqrt(50), 5}};
    }

    @Test(dataProvider = "objectsForCheckRectangular")
    public void testRectangular(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        Assert.assertEquals(triangle.detectTriangle(), 8);
    }
    //программа не всегда идентифицирует прямоугольные треугольники

    //2d
    @DataProvider
    public static Object[][] objectsForCheckRectangularIsosceles() {
        return new Object[][]{{5, 5, Math.sqrt(50)}, {Math.sqrt(50), 5, 5}, {5, Math.sqrt(50), 5}};
    }

    @Test(dataProvider = "objectsForCheckRectangularIsosceles")
    public void testRectangularAndIsosceles(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(triangle.checkTriangle());
        softAssert.assertEquals(triangle.detectTriangle(), 8);
        softAssert.assertEquals(triangle.detectTriangle(), 2);
        softAssert.assertAll();
    }
    //программа не идентифицирует прямоугольные равнобедренные треугольники сразу по 2 параметрам:
    // и равносторонний, и прямоугольный


    //3a
    @Test
    public void testSquareFotInt() {
        Triangle triangle = new Triangle(3, 4, 5);
        Assert.assertEquals(triangle.getSquare(), 6D);
    }
    //при передаче целочисленных значений площадь определяется верно

    //3b
    @Test
    public void testSquareFotDouble() {
        Triangle triangle = new Triangle(18, 15, 11);
        Assert.assertEquals(triangle.getSquare(), 82.31646250902671);
    }
    //при передаче дробных значений площадь определяется верно

    //3c
    @Test
    public void testSquareForLargeNumber() {
        Triangle triangle = new Triangle(3.555e+50, 3.555e+50, 3.555e+50);
        Assert.assertEquals(triangle.getSquare(), 5.472425351831413e+100);
    }
    //при передаче значений со степенью площадь определяется верно

    //3d
    @Test
    public void testSquareForSmallNumber() {
        Triangle triangle = new Triangle(3.555e-50, 3.555e-50, 3.555e-50);
        Assert.assertEquals(triangle.getSquare(), 5.472425351831416e-100);
    }
    //при передаче очень маленьких значений площадь определяется верно

    //3e
    @Test
    public void testOverFlowPerimeters() {
        Triangle triangle = new Triangle(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        double p = Double.MAX_VALUE + Double.MAX_VALUE + Double.MAX_VALUE;
        Assert.assertTrue(p!=Double.POSITIVE_INFINITY);
    }
    //при расчете периметра при максимальных значениях double происходит переполнение суммы

}
