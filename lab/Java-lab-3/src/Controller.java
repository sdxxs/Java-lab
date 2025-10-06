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
}
