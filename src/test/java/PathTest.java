import com.bamboggled.model.exceptions.NoPathException;
import com.bamboggled.model.path.Path;
import com.bamboggled.model.path.PathContainerUtils;
import com.bamboggled.model.path.Position;
import com.bamboggled.model.path.PossiblePathContainer;
import com.bamboggled.model.grid.BoggleGrid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class PathTest {

    BoggleGrid grid;

    @BeforeEach
    public void setUp(){
        this.grid = new BoggleGrid(4);
        this.grid.initalizeBoard("HELP" +
                                       "HOPE" +
                                       "TIME" +
                                       "ROAM");
    }

    @Test
    public void testStartPath() throws NoPathException {
        Path basePath = new Path(new Position(1,2));

        PossiblePathContainer initialContainer = new PossiblePathContainer();
        initialContainer.addPath(basePath);
        PossiblePathContainer endContainer = PathContainerUtils.fetchContainer(initialContainer, this.grid, 'E');

        HashSet<Path> expected = new HashSet<>();

        Path path1 = new Path(basePath, new Position(0, 1));
        Path path2 = new Path(basePath, new Position(1, 3));
        Path path3 = new Path(basePath, new Position(2, 3));

        expected.add(path1);
        expected.add(path2);
        expected.add(path3);

        assert expected.equals(new HashSet(endContainer.getPaths()));
    }

    @Test
    public void testContinuedPath() throws NoPathException {
        Path basePath = new Path(new Path(new Position(0, 0)), new Position(1, 1));

        PossiblePathContainer initialContainer = new PossiblePathContainer();
        initialContainer.addPath(basePath);
        PossiblePathContainer endContainer = PathContainerUtils.fetchContainer(initialContainer, this.grid, 'T');

        HashSet<Path> expected = new HashSet<>();

        Path path1 = new Path(basePath, new Position(2, 0));

        expected.add(path1);

        assert expected.equals(new HashSet(endContainer.getPaths()));
    }

    @Test
    public void testDisconnectedPath() {
        Path basePath = new Path(new Position(0, 0));

        PossiblePathContainer initialContainer = new PossiblePathContainer();
        initialContainer.addPath(basePath);
        try {
            PossiblePathContainer endContainer = PathContainerUtils.fetchContainer(initialContainer, this.grid, 'P');
        } catch (NoPathException e) {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    public void testNoSuchCharacter(){
        Path basePath = new Path(new Position(0, 0));

        PossiblePathContainer initialContainer = new PossiblePathContainer();
        initialContainer.addPath(basePath);
        try {
            PossiblePathContainer endContainer = PathContainerUtils.fetchContainer(initialContainer, this.grid, 'X');
        } catch (NoPathException e) {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    public void testUsedCharacter(){
        Path basePath = new Path(new Position(1, 0));
        basePath = new Path(basePath, new Position(1, 1));
        basePath = new Path(basePath, new Position(0, 1));
        basePath = new Path(basePath, new Position(0, 0));

        PossiblePathContainer initialContainer = new PossiblePathContainer();
        initialContainer.addPath(basePath);
        try {
            PossiblePathContainer endContainer = PathContainerUtils.fetchContainer(initialContainer, this.grid, 'O');
        } catch (NoPathException e) {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    public void testMultiplePaths() throws NoPathException {
        Path basePath1 = new Path(new Position(0,1));
        Path basePath2 = new Path(new Position(1, 3));
        Path basePath3 = new Path(new Position(2, 3));

        PossiblePathContainer initialContainer = new PossiblePathContainer();

        initialContainer.addPath(basePath1);
        initialContainer.addPath(basePath2);
        initialContainer.addPath(basePath3);

        PossiblePathContainer endContainer = PathContainerUtils.fetchContainer(initialContainer, this.grid, 'P');

        HashSet<Path> expected = new HashSet<>();

        Path path1_1 = new Path(basePath1, new Position(1, 2));
        Path path2_1 = new Path(basePath2, new Position(0, 3));
        Path path2_2 = new Path(basePath2, new Position(1, 2));
        Path path3_1 = new Path(basePath3, new Position(1, 2));

        expected.add(path1_1);
        expected.add(path2_1);
        expected.add(path2_2);
        expected.add(path3_1);

        assert expected.equals(new HashSet(endContainer.getPaths()));
    }

}
