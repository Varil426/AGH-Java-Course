public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(": ").append(adminLevel).append(" Area: ").append(area).append(" Density: ").append(density).append(" Population: ").append(population);
        return builder.toString();
    }
}