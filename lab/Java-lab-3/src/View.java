public class View {
    public String PrintAll = "Список набору даних:\n";
    public String AllAreas = "Сумарна площа всіх фігур набору даних: ";
    public String SumOfClass = "Сумарна площа фігур виду ";
    public String ComparedByArea = "Впорядковані за площею: \n";
    public String ComparedByColor = "Ворядковані за кольором: \n";
    
    public void Write(String message)
    {
        System.out.println(message);
    }
}
