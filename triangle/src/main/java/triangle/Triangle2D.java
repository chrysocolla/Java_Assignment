/*****************************************************
*                     Triangle2D                     *
*----------------------------------------------------*
* -p1: MyPoint                                       *
* -p2: MyPoint                                       *
* -p3: MyPoint                                       *
* +getP1(): MyPoint                                  *
* +getP2(): MyPoint                                  *
* +getP3(): MyPoint                                  *
* +Triangle2D()                                      *
* +Triangle2D(p1: MyPoint, p2: MyPoint, p3: MyPoint) *
* +getArea(): double                                 *
* +getPerimeter(): double                            *
* +contains(p: MyPoint): boolean                     *
* +contains(t: Triangle2D): boolean                  *
* +overlaps(t: Triangle2D): boolean                  *
*****************************************************/

package triangle;

import java.awt.geom.Line2D;

// Implement Triangle2D class
public class Triangle2D {
//#region data
    // Data fields
    private MyPoint p1;
    private MyPoint p2;
    private MyPoint p3;
//#endregion
//#region constructors
    /** Constructor that creates a triangle (0, 0), (1, 1), (2, 5) */
    Triangle2D() {
        p1 = new MyPoint();
        p2 = new MyPoint(1, 1);
        p3 = new MyPoint(2, 5);
    }

    /** Constructor a triangle with specified coordinates */
    Triangle2D(MyPoint p1, MyPoint p2, MyPoint p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
//#endregion
//#region getters
    /** Return p1 */
    public MyPoint getP1() {
        return p1;
    }

    /** Return p2 */
    public MyPoint getP2() {
        return p2;
    }

    /** Return p3 */
    public MyPoint getP3() {
        return p3;
    }
//#endregion
//#region methods
    public double getArea() {
        double d12 = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        double d23 = Math.sqrt(Math.pow(p2.getX() - p3.getX(), 2) + Math.pow(p2.getY() - p3.getY(), 2));
        double d13 = Math.sqrt(Math.pow(p1.getX() - p3.getX(), 2) + Math.pow(p1.getY() - p3.getY(), 2));
        double p = (d12 + d23 + d13) / 2 ;
        return Math.sqrt(p * (p - d12) * (p - d23) * (p - d13));
    }

    public double getPerimeter() {
        double d12 = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        double d23 = Math.sqrt(Math.pow(p2.getX() - p3.getX(), 2) + Math.pow(p2.getY() - p3.getY(), 2));
        double d13 = Math.sqrt(Math.pow(p1.getX() - p3.getX(), 2) + Math.pow(p1.getY() - p3.getY(), 2));
        return d12 + d23 + d13;
    }

    public boolean contains(MyPoint point) {
        if (Double.compare(getArea(),
                new Triangle2D(point, getP2(), getP3()).getArea() +
                new Triangle2D(getP1(), point, getP3()).getArea() +
                new Triangle2D(getP1(), getP2(), point).getArea()
            ) == 0) return true;
        return false;
    }

    public boolean contains(Triangle2D triangle2d) {
        return contains(triangle2d.p1) && contains(triangle2d.p2) && contains(triangle2d.p3);
    }

    public boolean overlaps(Triangle2D triangle2d) {
        final MyPoint[][] thisLines = {{p1, p2}, {p2, p3}, {p1, p3}};
        final MyPoint[][] thatLines = {{triangle2d.p1, triangle2d.p2},
                                       {triangle2d.p2, triangle2d.p3},
                                       {triangle2d.p1, triangle2d.p3}};
        for (MyPoint[] thisLine: thisLines)
            for (MyPoint[] thatLine : thatLines)
                if(Line2D.linesIntersect(
                    thisLine[0].getX(), thisLine[0].getY(), thisLine[1].getX(), thisLine[1].getY(),
                    thatLine[0].getX(), thatLine[0].getY(), thatLine[1].getX(), thatLine[1].getY()
                )) return true;
        return false;
    }
//#endregion
    public static void main(String[] args) {
        Triangle2D t1 = new Triangle2D(new MyPoint(2.5, 2), new MyPoint(4.2, 3), new MyPoint(5, 3.5));
        System.out.println(String.format("面积：%f", t1.getArea()));
        System.out.println(String.format("周长：%f", t1.getPerimeter()));
        System.out.println(String.format("(3, 3)%s三角形内",
            t1.contains(new MyPoint(3, 3)) ? "在" : "不在"));
        System.out.println(String.format("(2.9, 2)-(4, 1)-(1, 3.4)%s在三角形内",
            t1.contains(new Triangle2D(
                new MyPoint(2.9, 2), new MyPoint(4, 1), new MyPoint(1, 3.4))) ? "" : "不"));
        System.out.println(String.format("(2, 5.5)-(4, -3)-(2, 6.5)和三角形%s重叠",
            t1.overlaps(new Triangle2D(
                new MyPoint(2, 5.5), new MyPoint(4, -3), new MyPoint(2, 6.5))) ? "" : "不"));
    }
}
