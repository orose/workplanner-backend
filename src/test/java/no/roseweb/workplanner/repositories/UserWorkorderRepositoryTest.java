package no.roseweb.workplanner.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserWorkorderRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void testAddAssignment() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        Integer affectedRows = userWorkorderRepository.addAssignment(1L, 1L);
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    public void testAssignSameUserTwiceShouldNotFail() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        userWorkorderRepository.addAssignment(1L, 1L);
        Integer affectedRows = userWorkorderRepository.addAssignment(1L, 1L);
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    public void testRemoveAssignment() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        Integer rows = userWorkorderRepository.removeAssignment(1L, 1L);
        assertThat(rows).isEqualTo(1);

        rows = userWorkorderRepository.removeAssignment(1L, 1L);
        assertThat(rows).isEqualTo(0);
    }

    @Test
    public void testRemoveNonexistingAssignmentShouldReturnZeroUpdatedRows() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        userWorkorderRepository.removeAssignment(1L, 99999L);
        Integer rows = userWorkorderRepository.removeAssignment(1L, 99999L);

        assertThat(rows).isEqualTo(0);
    }

    @Test
    public void testRemoveAllAssignmentShouldReturnOneUpdatedRows() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        Integer rows = userWorkorderRepository.removeAllAssignments(1L);
        assertThat(rows).isEqualTo(1);

        rows = userWorkorderRepository.removeAllAssignments(1L);
        assertThat(rows).isEqualTo(0);
    }
}
