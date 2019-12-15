import finder.ClassNameFinder;
import finder.impl.ClassNameFinderImpl;

/**
 * @author aburulev on 15.12.2019.
 */
public class App {

    public static void main(String[] args) {
        ClassNameFinder classNameFinder = new ClassNameFinderImpl();

        classNameFinder.findClassNames(args[0], args[1]);
    }
}
