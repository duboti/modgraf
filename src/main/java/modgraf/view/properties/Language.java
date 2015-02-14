package modgraf.view.properties;

import java.util.Properties;

/**
 * Klasa zawiera wszystkie napisy, które użytkownik może zobaczyć, pogrupowane na języki.
 *
 * @author Daniel Pogrebniak
 */
public class Language
{

	public static Properties createPolishLanguage()
	{
		Properties lang = new Properties();
		lang.setProperty("language", "polski");
		
		lang.setProperty("menu-file", "Plik");
		lang.setProperty("menu-file-new", "Nowy");
		lang.setProperty("menu-file-open", "Otwórz");
		lang.setProperty("menu-file-save", "Zapisz");
		lang.setProperty("menu-file-saveas", "Zapisz jako");
        lang.setProperty("menu-file-save-text-result", "Zapisz rozwiązanie tekstowe");

		lang.setProperty("menu-vertex", "Wierzchołek");
		lang.setProperty("menu-vertex-add", "Dodaj wierzchołek");
		lang.setProperty("menu-vertex-delete", "Usuń zaznaczone");
		lang.setProperty("menu-vertex-fill-color", "Kolor wypełnienia");
		lang.setProperty("menu-vertex-size", "Rozmiar");
		lang.setProperty("menu-vertex-shape", "Kształt");
		lang.setProperty("menu-vertex-border", "Obramowanie");
		lang.setProperty("menu-vertex-font", "Czcionka");
		
		lang.setProperty("menu-vertex-size-small", "Mały");
		lang.setProperty("menu-vertex-size-medium", "Średni");
		lang.setProperty("menu-vertex-size-big", "Duży");
		lang.setProperty("menu-vertex-size-custom", "Niestandardowy");
		
		lang.setProperty("menu-vertex-shape-circle", "Koło");
		lang.setProperty("menu-vertex-shape-square", "Kwadrat");
		lang.setProperty("menu-vertex-shape-rhombus", "Romb");
		lang.setProperty("menu-vertex-shape-cloud", "Chmura");
		lang.setProperty("menu-vertex-shape-hexagon", "Sześciokąt");
		lang.setProperty("menu-vertex-shape-triangle", "Trójkąt");
		lang.setProperty("menu-vertex-shape-actor", "Aktor");
		lang.setProperty("menu-vertex-shape-cylinder", "Walec");
		
		lang.setProperty("menu-vertex-border-width", "Grubość");
		lang.setProperty("menu-vertex-border-color", "Kolor");
		
		lang.setProperty("menu-vertex-font-color", "Kolor");
		lang.setProperty("menu-vertex-font-family", "Krój");
		lang.setProperty("menu-vertex-font-size", "Rozmiar");

        lang.setProperty("menu-edge", "Krawędź");
        lang.setProperty("menu-edge-delete", "Usuń zaznaczone");
        lang.setProperty("menu-edge-width", "Grubość");
        lang.setProperty("menu-edge-color", "Kolor");

		lang.setProperty("menu-algorithm", "Algorytmy");
		lang.setProperty("menu-algorithm-add", "Dodaj nowy algorytm");
		
		lang.setProperty("menu-algorithm-shortest-path", "Najkrótsza ścieżka");
		lang.setProperty("menu-algorithm-maximum-flow", "Maksymalny przepływ");
		lang.setProperty("menu-algorithm-cheapest-flow", "Najtańszy przepływ");
		lang.setProperty("menu-algorithm-tsp", "Problem komiwojażera");
		lang.setProperty("menu-algorithm-chromatic-number", "Kolorowanie wierzchołków");
		lang.setProperty("menu-algorithm-edge-coloring", "Kolorowanie krawędzi");
		lang.setProperty("menu-algorithm-spanning-tree", "Minimalne drzewo rozpinające");
		lang.setProperty("menu-algorithm-eulerian-cycle", "Cykl Eulera");

        lang.setProperty("menu-algorithm-shortest-path-bf", "Bellman-Ford");
        lang.setProperty("menu-algorithm-shortest-path-d", "Dijkstra");
        lang.setProperty("menu-algorithm-shortest-path-fw", "Floyd-Warshall");
        lang.setProperty("menu-algorithm-maximum-flow-ek", "Edmonds–Karp");
        lang.setProperty("menu-algorithm-cheapest-flow-bg", "Busacker-Gowen");
        lang.setProperty("menu-algorithm-tsp-approximate", "Algorytm przybliżony");
        lang.setProperty("menu-algorithm-tsp-exact", "Algorytm dokładny");
        lang.setProperty("menu-algorithm-chromatic-number-greedy", "Algorytm zachłanny");
        lang.setProperty("menu-algorithm-edge-coloring-approximate", "Algorytm przybliżony");
        lang.setProperty("menu-algorithm-spanning-tree-k", "Kruskal");
        lang.setProperty("menu-algorithm-spanning-tree-p", "Prim");
        lang.setProperty("menu-algorithm-eulerian-cycle-h", "Hierholzer");

		lang.setProperty("menu-algorithm-steps", "Tryb krokowy");
		lang.setProperty("menu-algorithm-steps-dijkstra", "alg. Dijkstry");
		
		lang.setProperty("menu-utils", "Narzędzia");
		lang.setProperty("menu-utils-preferences", "Opcje");
		lang.setProperty("menu-utils-clear-styles", "Wyczyść style");

        lang.setProperty("menu-utils-converters", "Konwertery typów grafów");
        lang.setProperty("menu-utils-con-undirected", "na graf nieskierowany");
        lang.setProperty("menu-utils-con-unweighted", "na graf nieważony");
        lang.setProperty("menu-utils-con-weighted-cost", "na graf ważony (koszt)");
        lang.setProperty("menu-utils-con-weighted-capacity", "na graf ważony (przepustowość)");

		lang.setProperty("menu-utils-generators", "Generatory grafów");
		lang.setProperty("menu-utils-gen-complete", "Pełny");
		lang.setProperty("menu-utils-gen-random", "Losowy");
		lang.setProperty("menu-utils-gen-ring", "Pierścień");
		lang.setProperty("menu-utils-gen-scale-free", "Bezskalowy");
		lang.setProperty("menu-utils-gen-complete-bipartite", "Pełny dwudzielny");
		lang.setProperty("menu-utils-gen-random-flow", "Losowa sieć przepływowa");
		
		lang.setProperty("menu-help", "Pomoc");
		lang.setProperty("menu-help-help", "Pomoc");
		lang.setProperty("menu-help-about", "O programie");
		
		lang.setProperty("error", "Błąd");
		lang.setProperty("error-not-open-file", "Nie udało się otworzyć pliku!");
		lang.setProperty("error-not-save-file", "Nie udało się zapisać pliku!");
		lang.setProperty("error-not-graph", "Plik nie zawiera poprawnej definicji grafu!");
		lang.setProperty("error-not-supported-extension", "Nie rozpoznano typu pliku!");
		lang.setProperty("error-missing-data", "Brak danych do zapisania!");
		lang.setProperty("error-missing-graph-xml", "Plik nie zawiera opisu grafu w formacie XML!");
		lang.setProperty("error-not-open-jar-file", "Nie udało się otworzyć pliku lub plik nie zawiera definicji klas!");
		lang.setProperty("error-not-close-file", "Nie udało się zamknąć pliku!");
		lang.setProperty("error-not-read-class", "Nie udało się wczytać klasy!");
		lang.setProperty("error-not-Modgraf-algorithm", "Wybrana klasa nie jest poprawnym algorytmem programu Modgraf!");
		lang.setProperty("error-not-open-help-file", "Nie udało się otworzyć pliku pomocy: ");
		lang.setProperty("error-names-with-spaces", "Nazwy wierzchołków konwertowanego grafu nie mogą zawierać spacji!");

		lang.setProperty("warning", "Ostrzeżenie");
		lang.setProperty("warning-not-save-properties-file", "Nie udało się zapisać pliku z ustawieniami programu!");
		lang.setProperty("warning-not-load-properties-file", "Nie udało się wczytać pliku z ustawieniami programu!");
		lang.setProperty("warning-not-save-language-file", "Nie udało się zapisać pliku z domyślnym językiem!");
		lang.setProperty("warning-only-one-vertex", "Aby zmienić nazwę należy zaznaczyć tylko 1 wierzchołek!");
		lang.setProperty("warning-missing-style-definition", "Plik nie zawiera definicji styli!");
		lang.setProperty("warning-missing-graph-definition", "Plik nie zawiera definicji grafu!");
		lang.setProperty("warning-not-number", "Podana wartość nie jest poprawną liczbą!");
		lang.setProperty("warning-not-proper-number", "Podana wartość nie jest liczbą z zakresu ");
		lang.setProperty("warning-missing-edges", "Graf nie zawiera żadnych krawędzi!");
		lang.setProperty("warning-not-different-vertices", "Wierzchołki startowy i końcowy muszą się różnić!");
		lang.setProperty("warning-not-add-edge", "Nie można dodać krawędzi! Prawdopodobnie istniała już wcześniej.");
		lang.setProperty("warning-unweighted-edge", "Nie wolno ustawiać wag ani nazywać krawędzi w grafie nieważonym!");
		lang.setProperty("warning-name-exists", "Wierzchołek o podanej nazwie już istnieje!\nPodaj inną nazwę:");
		lang.setProperty("warning-not-number-double-weight-default", "Podana wartość nie jest formatu liczba/liczba (przepustowość/koszt)!\nWpisana została wartość domyślna: ");
		lang.setProperty("warning-not-number-weight-default", "Podana wartość nie jest liczbą!\nWpisana została wartość domyślna: ");
		lang.setProperty("warning-wrong-graph-type", "Niewłaściwy typ grafu do tego zadania!\nTen algorytm działa tylko na ");
		lang.setProperty("warning-not-generate", "Nie udało się stworzyć grafu o zadanych parametrach!");

		lang.setProperty("question-changes-exist", "Istnieją zmiany, które nie zostały zapisane.\nCzy na pewno chcesz kontynuować?");
		lang.setProperty("question-save-before-exit", "Czy chcesz zapisać zmiany przed wyjściem?");
		lang.setProperty("question-file-exists", "Plik o podanej nazwie już istnieje.\nCzy chcesz go zastąpić?");
		lang.setProperty("question-transparent-background", "Czy chcesz aby tło było przeźroczyste?");
		
		lang.setProperty("information", "Informacja");
		lang.setProperty("message-found-classes", "Wybierz klasę do dodania:");
		lang.setProperty("message-successfully-add-algorithm", "Poprawnie dodano algorytm ");
		lang.setProperty("message-new-vertex-name", "Wpisz nową nazwę wierzchołka:");
		lang.setProperty("message-new-edge-name", "Wpisz nową wagę krawędzi:");
		lang.setProperty("message-save-file", "Zapisano plik ");
		lang.setProperty("message-write-numbers", "Wpisz liczby rzeczywiste ");
		lang.setProperty("message-send-notes", "Uwagi do programu można wysyłać na adres");
		lang.setProperty("message-no-solution", "Nie znaleziono rozwiązania!");
		lang.setProperty("message-not-complete-graph", "Nie znaleziono rozwiązania!\nPrawdopodobnie graf nie jes pełny.");
		lang.setProperty("message-range-tip", "Liczby całkowite od");
        lang.setProperty("message-not-eulerian-graph", "Ten graf nie zawiera cyklu Eulera.");

		lang.setProperty("files-all", "Wszystkie wspierane formaty (*.xml;*.grf;*png)");
		lang.setProperty("files-jar", "Pliki JAR");
		lang.setProperty("files-grf", "Pliki programu Modgraf 2 (*.grf)");
		lang.setProperty("files-xml", "Pliki XML (*.xml)");
		lang.setProperty("files-png", "Pliki PNG+XML (*.png)");
		lang.setProperty("files-txt", "Pliki tekstowe (*.txt)");

		lang.setProperty("frame-new-graph-name", "Nowy graf");
		lang.setProperty("frame-new-graph", "Utwórz nowy graf");
		lang.setProperty("frame-generate-graph", "Generuj nowy graf");
		lang.setProperty("frame-found-classes", "Znalezione klasy");
		lang.setProperty("frame-change-name", "Zmień nazwę");
		lang.setProperty("frame-change-weight", "Zmień wagę");
		lang.setProperty("frame-select-fill-color", "Wybierz kolor wypełnienia");
		lang.setProperty("frame-select-border-color", "Wybierz kolor obramowania");
		lang.setProperty("frame-select-font-color", "Wybierz kolor czcionki");
		lang.setProperty("frame-select-font-family", "Wybierz czcionkę");
		lang.setProperty("frame-vartex-params", "Parametry wierzchołka");
		lang.setProperty("frame-change-line-width", "Podaj grubość krawędzi");
		lang.setProperty("frame-change-font-size", "Podaj rozmiar czcionki");
		lang.setProperty("frame-algorithm-params", "Parametry startowe");
		lang.setProperty("frame-algorithm-steps", "Krokowe wykonywanie algorytmu");
		lang.setProperty("frame-select-color", "Wybierz kolor");
		
		lang.setProperty("label-graph-type", "Typ grafu");
		lang.setProperty("label-edge-type", "Liczba parametrów krawędzi");
		lang.setProperty("label-number-of-vertices", "Ilość wierzchołków");
		lang.setProperty("label-number-of-edges", "Ilość krawędzi");
		lang.setProperty("label-height", "Wysokość");
		lang.setProperty("label-width", "Szerokość");
		lang.setProperty("label-start-vertex", "Wierzchołek startowy");
		lang.setProperty("label-end-vertex", "Wierzchołek końcowy");
		lang.setProperty("label-expected-flow", "Oczekiwany przepływ");
		lang.setProperty("label-program-name", "Nazwa programu");
		lang.setProperty("label-version", "Wersja");
		lang.setProperty("label-date", "Data");
		lang.setProperty("label-author", "Autor");
		
		lang.setProperty("button-new-graph", "Nowy graf");
		lang.setProperty("button-change-size", "Zmień rozmiar");
		lang.setProperty("button-run-algorithm", "Uruchom");
		lang.setProperty("button-close", "Zamknij");
		lang.setProperty("button-save", "Zapisz");
		lang.setProperty("button-cancel", "Anuluj");
		lang.setProperty("button-change", "Zmień");
		lang.setProperty("button-disable-show-distances", "Wyłącz wyświetlanie odległości");
		
		lang.setProperty("text-read-graph", "Wczytano poprawnie graf");
		lang.setProperty("text-create-graph", "Stworzono nowy graf");
		
		lang.setProperty("graph-type-directed", "skierowany");
		lang.setProperty("graph-type-undirected", "nieskierowany");
		
		lang.setProperty("edge-type-0", "nieważony");
		lang.setProperty("edge-type-1", "ważony");
		lang.setProperty("edge-type-2", "podwójnie ważony");
		
		lang.setProperty("from", "od");
		lang.setProperty("to", "do");
		lang.setProperty("yes", "tak");
		lang.setProperty("no", "nie");
		lang.setProperty("or", "albo");
		
		lang.setProperty("alg-sp-graph-type", "ważonych grafach.");
		lang.setProperty("alg-cn-graph-type", "nieskierowanych grafach.");
		lang.setProperty("alg-hc-graph-type", "pełnych nieskierowanych ważonych grafach.");
		lang.setProperty("alg-mf-graph-type", "ważonych skierowanych grafach.");
		lang.setProperty("alg-ec-graph-type", "nieskierowanych grafach.");

		lang.setProperty("alg-sp-message-1", "Najkrótsza ścieżka między wierzchołkami ");
		lang.setProperty("alg-sp-message-2", " i ");
		lang.setProperty("alg-sp-message-3", " przechodzi przez ");
		lang.setProperty("alg-sp-message-4", "bezpośrednią krawędź.\n");
		lang.setProperty("alg-sp-message-5", " krawędzie.\n");
		lang.setProperty("alg-sp-message-6", " krawędzi.\n");
		lang.setProperty("alg-sp-message-7", "Ścieżka przechodzi kolejno przez wierzchołki: ");
		lang.setProperty("alg-sp-message-8", " ma długość równą ");
		
		lang.setProperty("alg-cn-message-1", "Minimalna liczba kolorów: ");
		lang.setProperty("alg-cn-message-2", "\nGrupy wierzchołków o tych samych kolorach:\n");
		lang.setProperty("alg-hc-message-1", "Najkrótsza ścieżka przechodzi kolejno przez wierzchołki: ");
		lang.setProperty("alg-mf-message-1", "Maksymalny przepływ: ");
		lang.setProperty("alg-mf-message-2", "Przepływ odbywa się krawędziami: ");
		lang.setProperty("alg-ec-message-1", "Liczba użytych kolorów: ");
		lang.setProperty("alg-ec-message-2", "\nGrupy krawędzi o tych samych kolorach:\n");
		
		lang.setProperty("alg-bg-message-1", "Ilość przesłanych jednostek:");
		lang.setProperty("alg-bg-message-2", "Wykorzystane krawędzie:\n");
		lang.setProperty("alg-bg-message-3", "Koszt przesyłu:");
		lang.setProperty("alg-bg-error-1", "Nie znaleziono wierzchołka");
		lang.setProperty("alg-bg-error-2", "Nie znaleziono rozwiązania dopuszczalnego");
		lang.setProperty("alg-bg-error-3", "Zły format danych wejściowych (powinna być liczba)");

        lang.setProperty("alg-st-message-1", "Suma wag w minimalnym drzewie rozpinającym wynosi: ");
        lang.setProperty("alg-st-message-2", "Minimalne drzewo rozpinające zawiera ");
        lang.setProperty("alg-st-message-3", " krawędzi.");
        lang.setProperty("alg-st-message-4", "\nKrawędzie należące do drzewa:\n");

		lang.setProperty("pref-generalTab-name", "Ogólne");
		lang.setProperty("pref-general-graph-type", "Domyślny typ grafu");
		lang.setProperty("pref-general-edge-type", "Domyślna liczba parametrów krawędzi");
		lang.setProperty("pref-general-language", "Język");
		lang.setProperty("pref-general-startup", "Wyświetlanie okna \""+lang.getProperty("frame-new-graph")+"\" przy starcie programu");
		lang.setProperty("pref-general-window-width", "Początkowa szerokość okna");
		lang.setProperty("pref-general-graphPane-height", "Wysokość panelu z grafem");
		lang.setProperty("pref-general-textPane-height", "Wysokość panelu tekstowego");
		lang.setProperty("pref-general-file-format", "Domyślny format zapisu plików");
		lang.setProperty("pref-general-file-encoding", "Kodowanie znaków w plikach");
		lang.setProperty("pref-general-background-color", "Kolor tła");
		
		lang.setProperty("pref-vertexTab-name", "Wierzchołek");
		lang.setProperty("pref-vertex-fill-color", "Domyślny kolor wypełnienia");
		lang.setProperty("pref-vertex-border-color", "Domyślny kolor obramowania");
		lang.setProperty("pref-vertex-height", "Domyślna wysokość");
		lang.setProperty("pref-vertex-width", "Domyślna szerokość");
		lang.setProperty("pref-vertex-shape", "Domyślny kształt");
		lang.setProperty("pref-vertex-border-width", "Domyślna grubość obramowania");
		lang.setProperty("pref-vertex-font-family", "Domyślna czcionka w nazwach");
		lang.setProperty("pref-vertex-font-size", "Domyślny rozmiar czcionki w nazwach");
		lang.setProperty("pref-vertex-font-color", "Domyślny kolor czcionki w nazwach");
		
		lang.setProperty("pref-edgeTab-name", "Krawędź");
		lang.setProperty("pref-edge-width", "Domyślna grubość");
		lang.setProperty("pref-edge-color", "Domyślny kolor");
		lang.setProperty("pref-edge-weight", "Domyślna wartość w grafie ważonym");
		lang.setProperty("pref-edge-capacity", "Domyślna pierwsza wartość w grafie podwójnie ważonym");
		lang.setProperty("pref-edge-cost", "Domyślna druga wartość w grafie podwójnie ważonym");
		lang.setProperty("pref-edge-font-size", "Domyślny rozmiar czcionki");
		lang.setProperty("pref-edge-font-color", "Domyślny kolor czcionki");
		
		return lang;
	}

	public static Properties createEnglishLanguage()
	{
		Properties lang = new Properties();
		lang.setProperty("language", "english");
		
		lang.setProperty("menu-file", "File");
		lang.setProperty("menu-file-new", "New");
		lang.setProperty("menu-file-open", "Open");
		lang.setProperty("menu-file-save", "Save");
		lang.setProperty("menu-file-saveas", "Save as");
		lang.setProperty("menu-file-save-text-result", "Save text result");

		lang.setProperty("menu-vertex", "Vertex");
		lang.setProperty("menu-vertex-add", "Add vertex");
		lang.setProperty("menu-vertex-delete", "Delete selected");
		lang.setProperty("menu-vertex-fill-color", "Fill color");
		lang.setProperty("menu-vertex-border", "Border");
		lang.setProperty("menu-vertex-size", "Size");
		lang.setProperty("menu-vertex-shape", "Shape");
		lang.setProperty("menu-vertex-font", "Font");
		
		lang.setProperty("menu-vertex-size-small", "Small");
		lang.setProperty("menu-vertex-size-medium", "Medium");
		lang.setProperty("menu-vertex-size-big", "Big");
		lang.setProperty("menu-vertex-size-custom", "Custom");
		
		lang.setProperty("menu-vertex-shape-circle", "Circle");
		lang.setProperty("menu-vertex-shape-square", "Square");
		lang.setProperty("menu-vertex-shape-rhombus", "Rhombus");
		lang.setProperty("menu-vertex-shape-cloud", "Cloud");
		lang.setProperty("menu-vertex-shape-hexagon", "Hexagon");
		lang.setProperty("menu-vertex-shape-triangle", "Triangle");
		lang.setProperty("menu-vertex-shape-actor", "Actor");
		lang.setProperty("menu-vertex-shape-cylinder", "Cylinder");
		
		lang.setProperty("menu-vertex-border-width", "Width");
		lang.setProperty("menu-vertex-border-color", "Color");
		
		lang.setProperty("menu-vertex-font-color", "Color");
		lang.setProperty("menu-vertex-font-family", "Family");
		lang.setProperty("menu-vertex-font-size", "Size");

        lang.setProperty("menu-edge", "Edge");
        lang.setProperty("menu-edge-delete", "Delete selected");
        lang.setProperty("menu-edge-width", "Width");
        lang.setProperty("menu-edge-color", "Color");

		lang.setProperty("menu-algorithm", "Algorithms");
		lang.setProperty("menu-algorithm-add", "Add new algorithm");
		lang.setProperty("menu-algorithm-generator", "Complete graph generator");
		
		lang.setProperty("menu-algorithm-shortest-path", "Shortest path");
		lang.setProperty("menu-algorithm-maximum-flow", "Maximum flow");
		lang.setProperty("menu-algorithm-cheapest-flow", "Cheapest flow");
		lang.setProperty("menu-algorithm-tsp", "Travelling salesman problem");
		lang.setProperty("menu-algorithm-chromatic-number", "Chromatic number");
		lang.setProperty("menu-algorithm-edge-coloring", "Edge coloring");
        lang.setProperty("menu-algorithm-spanning-tree", "Minimum spanning tree");
        lang.setProperty("menu-algorithm-eulerian-cycle", "Eulerian cycle");
		
		lang.setProperty("menu-algorithm-shortest-path-bf", "Bellman-Ford");
		lang.setProperty("menu-algorithm-shortest-path-d", "Dijkstra");
		lang.setProperty("menu-algorithm-shortest-path-fw", "Floyd-Warshall");
        lang.setProperty("menu-algorithm-maximum-flow-ek", "Edmonds–Karp");
        lang.setProperty("menu-algorithm-cheapest-flow-bg", "Busacker-Gowen");
        lang.setProperty("menu-algorithm-tsp-approximate", "Approximate algorithm");
        lang.setProperty("menu-algorithm-tsp-exact", "Exact algorithm");
        lang.setProperty("menu-algorithm-chromatic-number-greedy", "Greedy algorithm");
        lang.setProperty("menu-algorithm-edge-coloring-approximate", "Approximate algorithm");
        lang.setProperty("menu-algorithm-spanning-tree-k", "Kruskal");
        lang.setProperty("menu-algorithm-spanning-tree-p", "Prim");
        lang.setProperty("menu-algorithm-eulerian-cycle-h", "Hierholzer");

		lang.setProperty("menu-algorithm-steps", "Step mode");
		lang.setProperty("menu-algorithm-steps-dijkstra", "Dijkstra");

		lang.setProperty("menu-utils", "Utils");
		lang.setProperty("menu-utils-preferences", "Preferences");
		lang.setProperty("menu-utils-clear-styles", "Clear styles");

        lang.setProperty("menu-utils-converters", "Graph types converters");
        lang.setProperty("menu-utils-con-undirected", "undirected");
        lang.setProperty("menu-utils-con-unweighted", "unweighted");
        lang.setProperty("menu-utils-con-weighted-cost", "weighted (cost)");
        lang.setProperty("menu-utils-con-weighted-capacity", "weighted (capacity)");

		lang.setProperty("menu-utils-generators", "Graph generators");
		lang.setProperty("menu-utils-gen-complete", "Complete");
		lang.setProperty("menu-utils-gen-random", "Random");
		lang.setProperty("menu-utils-gen-ring", "Ring");
		lang.setProperty("menu-utils-gen-scale-free", "Scale free");
		lang.setProperty("menu-utils-gen-complete-bipartite", "Complete bipartite");
		lang.setProperty("menu-utils-gen-random-flow", "Random flow");
		
		lang.setProperty("menu-help", "Help");
		lang.setProperty("menu-help-help", "Help");
		lang.setProperty("menu-help-about", "About Modgraf");
		
		lang.setProperty("error", "Error");
		lang.setProperty("error-not-open-file", "Could not open file!");
		lang.setProperty("error-not-save-file", "Could not save file!");
		lang.setProperty("error-not-graph", "The file does not contain the correct definition of the graph!");
		lang.setProperty("error-not-supported-extension", "Not supported file extension!");
		lang.setProperty("error-missing-data", "No data to save!");
		lang.setProperty("error-missing-graph-xml", "The file does not contain a description of the graph in XML format!");
		lang.setProperty("error-not-open-jar-file", "Failed to open the file or the file does not contain a definition of classes!");
		lang.setProperty("error-not-close-file", "Failed to close file!");
		lang.setProperty("error-not-read-class", "Failed to load class!");
		lang.setProperty("error-not-Modgraf-algorithm", "The selected class is not a valid algorithm of the Modgraf program!");
		lang.setProperty("error-not-open-help-file", "Failed to open the help file: ");
        lang.setProperty("error-names-with-spaces", "Vertex has name with space!");
		
		lang.setProperty("warning", "Warning");
		lang.setProperty("warning-not-save-properties-file", "Failed to save the file with the program properties!");
		lang.setProperty("warning-not-load-properties-file", "Could not load file with the program properties!");
		lang.setProperty("warning-not-save-language-file", "Failed to save the file with the default language!");
		lang.setProperty("warning-only-one-vertex", "To change the name, select only one vertex!");
		lang.setProperty("warning-missing-style-definition", "The file does not contain a definition of style!");
		lang.setProperty("warning-missing-graph-definition", "The file does not contain a definition of graph!");
		lang.setProperty("warning-not-number", "The specified value is not a valid number!");
		lang.setProperty("warning-not-proper-number", "The specified value is not a number in the range ");
		lang.setProperty("warning-missing-edges", "Graph does not contain any edge!");
		lang.setProperty("warning-not-different-vertices", "Start and end vertices must be different!");
		lang.setProperty("warning-not-add-edge", "Could not add the edge! Probably already existed.");
		lang.setProperty("warning-unweighted-edge", "Must not set weights or name the edges in the unweighted graph!");
		lang.setProperty("warning-name-exists", "The vertex of the same name already exists!\nEnter another name:");
		lang.setProperty("warning-not-number-double-weight-default", "The specified value is not a format number/number (capacity/cost)!\nSet the default value: ");
		lang.setProperty("warning-not-number-weight-default", "The specified value is not a number!\nSet the default value: ");
		lang.setProperty("warning-wrong-graph-type", "Wrong type of graph for this task!\nThis algorithm works only on ");
        lang.setProperty("warning-not-generate", "Failed to create a graph of the given parameters!");

        lang.setProperty("question-changes-exist", "There are changes that have not been saved.\nAre you sure you want to continue?");
		lang.setProperty("question-save-before-exit", "Do you want to save your changes before exiting?");
		lang.setProperty("question-file-exists", "A file with the same name already exists.\nDo you want to replace it?");
		lang.setProperty("question-transparent-background", "Do you want to be transparent background?");
		
		lang.setProperty("information", "Information");
		lang.setProperty("message-found-classes", "Select the class to be added:");
		lang.setProperty("message-successfully-add-algorithm", "Successfully added the algorithm ");
		lang.setProperty("message-new-vertex-name", "Enter a new name for the vertex:");
		lang.setProperty("message-new-edge-name", "Enter a new weight for the edge:");
		lang.setProperty("message-save-file", "Saved file ");
		lang.setProperty("message-write-numbers", "Enter a real numbers ");
		lang.setProperty("message-send-notes", "Comments to the program can be sent to the address");
		lang.setProperty("message-no-solution", "Not found solution!");
		lang.setProperty("message-not-complete-graph", "Not found solution!\nProbably graph is not complete.");
		lang.setProperty("message-range-tip", "integers from");
		lang.setProperty("message-not-eulerian-graph", "This graph is not Eulerian.");

		lang.setProperty("files-all", "All supported types (*.xml;*.grf;*png)");
		lang.setProperty("files-jar", "Java ARchive (*.jar)");
		lang.setProperty("files-grf", "Modgraf 2 format (*.grf)");
		lang.setProperty("files-xml", "Extensible Markup Language (*.xml)");
		lang.setProperty("files-png", "Portable Network Graphics + XML (*.png)");
        lang.setProperty("files-txt", "Normal text file (*.txt)");
		
		lang.setProperty("frame-new-graph-name", "New graph");
		lang.setProperty("frame-new-graph", "Create new graph");
		lang.setProperty("frame-generate-graph", "Generate new graph");
		lang.setProperty("frame-found-classes", "Found classes");
		lang.setProperty("frame-change-name", "Rename");
		lang.setProperty("frame-change-weight", "Change weight");
		lang.setProperty("frame-select-fill-color", "Select fill color");
		lang.setProperty("frame-select-border-color", "Select border color");
		lang.setProperty("frame-select-font-color", "Select font color");
		lang.setProperty("frame-select-font-family", "Select font family");
		lang.setProperty("frame-vartex-params", "Vertex properties");
		lang.setProperty("frame-change-line-width", "Change line width");
		lang.setProperty("frame-change-font-size", "Change font size");
		lang.setProperty("frame-algorithm-params", "Algorithm start properties");
		lang.setProperty("frame-algorithm-steps", "Execute steps algorithm");
		lang.setProperty("frame-select-color", "Select color");
		
		lang.setProperty("label-graph-type", "Graph type");
		lang.setProperty("label-edge-type", "Number of edge parameters");
		lang.setProperty("label-number-of-vertices", "Number of vertices");
		lang.setProperty("label-number-of-vertices", "Number of edges");
		lang.setProperty("label-height", "Height");
		lang.setProperty("label-width", "Width");
		lang.setProperty("label-start-vertex", "Start vertex");
		lang.setProperty("label-end-vertex", "End vertex");
		lang.setProperty("label-expected-flow", "Expected flow");
		lang.setProperty("label-program-name", "Program name");
		lang.setProperty("label-version", "Version");
		lang.setProperty("label-date", "Date");
		lang.setProperty("label-author", "Author");
		
		lang.setProperty("button-new-graph", "New graph");
		lang.setProperty("button-change-size", "Change size");
		lang.setProperty("button-run-algorithm", "Run");
		lang.setProperty("button-close", "Close");
		lang.setProperty("button-save", "Save");
		lang.setProperty("button-cancel", "Cancel");
		lang.setProperty("button-change", "Change");
		lang.setProperty("button-disable-show-distances", "Disable show distances");
		
		lang.setProperty("text-read-graph", "Loaded properly graph");
		lang.setProperty("text-create-graph", "Created new graph");
		
		lang.setProperty("graph-type-directed", "directed");
		lang.setProperty("graph-type-undirected", "undirected");
		
		lang.setProperty("edge-type-0", "unweighted");
		lang.setProperty("edge-type-1", "weighted");
		lang.setProperty("edge-type-2", "double weighted");
		
		lang.setProperty("from", "from");
		lang.setProperty("to", "to");
		lang.setProperty("yes", "yes");
		lang.setProperty("no", "no");
		lang.setProperty("or", "or");
		
		lang.setProperty("alg-sp-graph-type", "weighted graphs.");
		lang.setProperty("alg-cn-graph-type", "undirected graphs.");
		lang.setProperty("alg-hc-graph-type", "completed undirected weighted graphs.");
		lang.setProperty("alg-mf-graph-type", "directed weighted graphs.");
        lang.setProperty("alg-ec-graph-type", "undirected graphs.");
		
		lang.setProperty("alg-sp-message-1", "The shortest path from ");
		lang.setProperty("alg-sp-message-2", " to ");
		lang.setProperty("alg-sp-message-3", " includes ");
		lang.setProperty("alg-sp-message-4", "direct edge.\n");
		lang.setProperty("alg-sp-message-5", " edges.\n");
		lang.setProperty("alg-sp-message-6", " edge.\n");
		lang.setProperty("alg-sp-message-7", "The path passes successively through the vertices: ");
		lang.setProperty("alg-sp-message-8", " have length ");
		
		lang.setProperty("alg-cn-message-1", "The minimum number of colors: ");
		lang.setProperty("alg-cn-message-2", "\nA group of vertices of the same color:\n");
		lang.setProperty("alg-hc-message-1", "The shortest path passes sequentially through the vertices: ");
		lang.setProperty("alg-mf-message-1", "Maximum flow: ");
		lang.setProperty("alg-mf-message-2", "The flow use edges: ");
		lang.setProperty("alg-ec-message-1", "Number of used colors: ");
		lang.setProperty("alg-ec-message-2", "\nEdges group of the same color:\n");
		
		lang.setProperty("alg-bg-message-1", "Number of sent units:");
		lang.setProperty("alg-bg-message-2", "Edges used to transport units:\n");
		lang.setProperty("alg-bg-message-3", "Cost of transport:");
		lang.setProperty("alg-bg-error-1", "Vertex not found");
		lang.setProperty("alg-bg-error-2", "No solution found in given graph");
		lang.setProperty("alg-bg-error-3", "Bad input format (should be numeric)");

        lang.setProperty("alg-st-message-1", "The sum of the weights in the minimum spanning tree is: ");
        lang.setProperty("alg-st-message-2", "Minimum spanning tree contains ");
        lang.setProperty("alg-st-message-3", " edges.");
        lang.setProperty("alg-st-message-4", "\nEdges belonging to the tree:\n");

		lang.setProperty("pref-generalTab-name", "General");
		lang.setProperty("pref-general-graph-type", "Default graph type");
		lang.setProperty("pref-general-edge-type", "Default number of edge parameters");
		lang.setProperty("pref-general-language", "Language");
		lang.setProperty("pref-general-startup", "Show window \""+lang.getProperty("frame-new-graph")+"\" on startup");
		lang.setProperty("pref-general-window-width", "Main window width");
		lang.setProperty("pref-general-graphPane-height", "Graph pane height");
		lang.setProperty("pref-general-textPane-height", "Text pane height");
		lang.setProperty("pref-general-file-format", "Default file format");
		lang.setProperty("pref-general-file-encoding", "File encoding");
		lang.setProperty("pref-general-background-color", "Background color");
		
		lang.setProperty("pref-vertexTab-name", "Vertex");
		lang.setProperty("pref-vertex-fill-color", "Default fill color");
		lang.setProperty("pref-vertex-border-color", "Default border color");
		lang.setProperty("pref-vertex-height", "Default height");
		lang.setProperty("pref-vertex-width", "Default width");
		lang.setProperty("pref-vertex-shape", "Default shape");
		lang.setProperty("pref-vertex-border-width", "Default border width");
		lang.setProperty("pref-vertex-font-family", "Default font family in names");
		lang.setProperty("pref-vertex-font-size", "Default font size in names");
		lang.setProperty("pref-vertex-font-color", "Default font color in names");
		
		lang.setProperty("pref-edgeTab-name", "Edge");
		lang.setProperty("pref-edge-width", "Default width");
		lang.setProperty("pref-edge-color", "Default color");
		lang.setProperty("pref-edge-weight", "Default value in weighted graph");
		lang.setProperty("pref-edge-capacity", "Default first value in double weighted graph");
		lang.setProperty("pref-edge-cost", "Default second value in double weighted graph");
		lang.setProperty("pref-edge-font-size", "Default font size");
		lang.setProperty("pref-edge-font-color", "Default font color");
		
		return lang;
	}

}
