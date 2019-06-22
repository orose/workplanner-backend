package no.roseweb.workplanner.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WorkorderListResponseTest {

    @Test
    public void testGettersAndSetters() {
        List<Workorder> list = new ArrayList<>();
        list.add(new Workorder());

        WorkorderListResponse response = new WorkorderListResponse();
        response.setData(list);
        response.setOffset(1);
        response.setLimit(2);
        response.setTotal(3);

        assertEquals(1, response.getOffset().longValue());
        assertEquals(2, response.getLimit().longValue());
        assertEquals(3, response.getTotal().longValue());
        assertNotNull(response.getData());
    }
}
