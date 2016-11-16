package seng302.workspace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Tests for project class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectTest {

    private Project project = Project.getInstance();


    public void testResetProject() throws Exception {
        project.markDirty();
        project.createNewProject();
        assertEquals(false, project.isDirty());
    }

    @Test
    public void testIsChanges() throws Exception {
        project.createNewProject();
        project.markDirty();
        assertEquals(true, project.isDirty());
    }
}