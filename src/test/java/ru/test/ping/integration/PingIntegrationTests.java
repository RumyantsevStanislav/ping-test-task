package ru.test.ping.integration;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;
import ru.test.ping.CustomPageImpl;
import ru.test.ping.entities.Execution;
import ru.test.ping.entities.dtos.ExecutionDto;
import ru.test.ping.repositories.ExecutionRepository;

import java.util.List;

import static ru.test.ping.utils.Consts.RequestParams.ADDRESS;
import static ru.test.ping.utils.Consts.RequestPaths.EXECUTE;
import static ru.test.ping.utils.Consts.RequestPaths.ROOT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PingIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExecutionRepository executionRepository;

    @Autowired
    private SessionFactory sessionFactory;

    // TODO: 10.07.2024 Это всё, конечно, должно быть в разных методах.
    @Test
    public void fullRestTest() throws InterruptedException {
        ResponseEntity<CustomPageImpl<ExecutionDto>> emptyExecutionList = restTemplate.exchange(
                ROOT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(emptyExecutionList.hasBody() && emptyExecutionList.getBody().isEmpty());
        String url = UriComponentsBuilder.fromPath(ROOT + EXECUTE)
                .queryParam(ADDRESS, "localhost").toUriString();

        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);
        restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);
        Assertions.assertEquals(1, stats.getEntityInsertCount());
        // TODO: 10.07.2024 Есть более элегантные решения через Future, ранее их не применял, надо разбираться.
        Thread.sleep(5_000);
        Assertions.assertEquals(2, stats.getEntityUpdateCount());
        List<Execution> executions = executionRepository.findAll();
        Assertions.assertEquals(1, executions.size());

    }


}
