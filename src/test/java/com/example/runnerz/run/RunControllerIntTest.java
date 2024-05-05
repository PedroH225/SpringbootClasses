package com.example.runnerz.run;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RunControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = restClient.get()
                .uri("/api/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertEquals(10, runs.size());
    }

    @Test
    void shouldFindRunById() {
        Run run = restClient.get()
                .uri("/api/runs/2")
                .retrieve()
                .body(Run.class);

        assertAll(
                () -> assertEquals(2, run.id()),
                () -> assertEquals("Afternoon Run", run.title()),
                () -> assertEquals("2024-02-22T12:16", run.startedOn().toString()),
                () -> assertEquals("2024-02-22T14:27", run.completedOn().toString()),
                () -> assertEquals(6, run.miles()),
                () -> assertEquals(Location.OUTDOOR, run.locationn()),
                () -> assertEquals(0, run.versionn()));
    }

    @Test
    void shouldCreateNewRun() {
        Run run = new Run(11, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 10,
                Location.OUTDOOR, null);

        ResponseEntity<Void> newRun = restClient.post()
                .uri("/api/runs")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        assertEquals(201, newRun.getStatusCodeValue());
    }

    @Test
    void shouldUpdateExistingRun() {
        Run run = restClient.get().uri("/api/runs/2").retrieve().body(Run.class);

        ResponseEntity<Void> updatedRun = restClient.put()
                .uri("/api/runs/2")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        assertEquals(204, updatedRun.getStatusCodeValue());
    }

    @Test
    void shouldDeleteRun() {
        ResponseEntity<Void> run = restClient.delete()
                .uri("/api/runs/2")
                .retrieve()
                .toBodilessEntity();

        assertEquals(204, run.getStatusCodeValue());
    }

}
