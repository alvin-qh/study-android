package alvin.lib.common.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JacksonTest {

    private final ObjectMapper mapper = Jackson.create();

    @Test
    public void test_local_date() throws Exception {
        TestModel model = new TestModel("s-1", LocalDate.of(2018, 1, 1));

        final String json = mapper.writeValueAsString(model);
        assertThat(json, is("{\"id\":\"s-1\",\"date\":\"2018-01-01\"}"));

        model = mapper.readValue(json, TestModel.class);
        assertThat(model.getId(), is("s-1"));
        assertThat(model.getDate().toString(), is("2018-01-01"));
    }

    public static class TestModel {
        private final String id;
        private final LocalDate date;

        @JsonCreator
        public TestModel(@JsonProperty("id") final String id,
                         @JsonProperty("date") final LocalDate date) {
            this.id = id;
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}
