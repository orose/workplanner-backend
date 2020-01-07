package no.roseweb.workplanner.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserWorkorderRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void testAddAssignment() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        Integer affectedRows = userWorkorderRepository.addAssignment("test@email.com", 1L);
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test(expected = DuplicateKeyException.class)
    public void testAssignSameUserTwiceShouldFail() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        userWorkorderRepository.addAssignment("test@email.com", 1L);
        userWorkorderRepository.addAssignment("test@email.com", 1L);
    }

    @Test
    public void testRemoveAssignment() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        Integer rows = userWorkorderRepository.removeAssignment("test@email.com", 1L);
        assertThat(rows).isEqualTo(1);

        rows = userWorkorderRepository.removeAssignment("test@email.com", 1L);
        assertThat(rows).isEqualTo(0);
    }

    @Test
    public void testRemoveNonexistingAssignmentShouldReturnZeroUpdatedRows() {
        UserWorkorderRepository userWorkorderRepository = new UserWorkorderRepositoryImpl(namedParameterJdbcTemplate);
        userWorkorderRepository.removeAssignment("test@email.com", 99999L);
        Integer rows = userWorkorderRepository.removeAssignment("test@email.com", 99999L);

        assertThat(rows).isEqualTo(0);
    }
}