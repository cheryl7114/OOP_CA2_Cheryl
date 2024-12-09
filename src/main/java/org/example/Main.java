package org.example;

import java.util.ArrayList;

/**
 * Name: Cheryl Kong
 * Class Group: SD2B
 */
public class Main {
    public static void main(String[] args) {
        ContainerManager manager = new ContainerManager();

        manager.add(new Box(10,5,8,2.5));
        manager.add(new Pyramid(3.8,8,5));
        manager.add(new Cylinder(20,8,7.3));
        manager.add(new Box(15,8,10,10));
        manager.add(new Pyramid(8.8,12,6.7));
        manager.add(new Cylinder(5,2,3));

        System.out.println("Total weight: " + manager.totalWeight());
        System.out.println("Total rectangular volume: " + manager.totalRectangularVolume());

        ArrayList<IMeasurableContainer> containers = manager.getAllContainers();
        for (IMeasurableContainer container : containers) {
            if (container instanceof Box box) {
                System.out.println("Box - Length: " + box.getLength() +
                        ", Width: " + box.getWidth() +
                        ", Depth: " + box.getDepth() +
                        ", Weight: " + box.weight());
            } else if (container instanceof Pyramid pyramid) {
                System.out.println("Pyramid - Length: " + pyramid.getLength() +
                        ", Side Length: " + pyramid.getSideLength() +
                        ", Weight: " + pyramid.weight());
            } else if (container instanceof Cylinder cylinder) {
                System.out.println("Cylinder - Height: " + cylinder.getHeight() +
                        ", Diameter: " + cylinder.getDiameter() +
                        ", Weight: " + cylinder.weight());
            }
        }
    }
}