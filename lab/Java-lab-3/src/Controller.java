public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model){
        this.view = view;
        this.model = model;
    }

    void PrintAllObjects(){
        view.Write(view.PrintAll + model.PrintAllObjects());    
    }

    void SumOfAreas(){
        view.Write(view.AllAreas + model.SumOfAreas()); 
    }

    void CompareByArea(){
        view.Write(view.ComparedByArea + model.CompareByArea());
    }

    void CompareByColor(){
        view.Write(view.ComparedByColor + model.CompareByColor());
    }

    void SumOfClass(Class ChosenClass){
        view.Write(view.SumOfClass + ChosenClass.getName() + "рівна " + model.SumOfClass(ChosenClass));
    }

    void Save(String path) {
        try {
            model.saveToFile(path);
            view.Write("Збережено у файл: " + path);
        } catch (Exception e) {
            view.Write("Помилка збереження: " + e.getMessage());
        }
    }

    void Load(String path) {
        try {
            model.loadFromFile(path);
            view.Write("Завантажено з файлу: " + path);
        } catch (Exception e) {
            view.Write("Помилка читання: " + e.getMessage());
        }
    }

}
