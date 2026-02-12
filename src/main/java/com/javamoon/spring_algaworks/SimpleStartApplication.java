package com.javamoon.spring_algaworks;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
public class SimpleStartApplication {
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DataIndex(Dissect dissect) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Dissect(String index, String program, String host) {}

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(SpringAlgaworksApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        // try {
        //     OpenSearchClient client = applicationContext.getBean(OpenSearchClient.class);
        //     HealthResponse health = client.cluster().health();        
        //     System.out.println(">>>>>>" + health.clusterName());

        //     String indexName = "fb-*";
        //     // SearchRequest request = new SearchRequest.Builder()
        //     //     .index(indexName)
        //     //     .query(q -> q
        //     //         .range(r -> r
        //     //             .field("dissect.time")
        //     //             .gte(JsonData.of("2025-02-28T11:30:00"))
        //     //             .lte(JsonData.of("2025-02-28T11:31:00"))
        //     //         )
        //     //     )
        //     //     .build();

        //     SearchRequest request = new SearchRequest.Builder()
        //         .index(indexName)
        //         .query(q -> q
        //             .wildcard(w -> w
        //                 .field("host.name")
        //                 .value("arch-ramon")
        //                 .caseInsensitive(true)
        //             )
        //         ).size(1)
        //         .build();

        //     SearchResponse<DataIndex> searchResponse = client.search(request, DataIndex.class);

        //     System.out.println(searchResponse.hits().hits().isEmpty());
        //     System.out.println(searchResponse.hits().hits().get(0).index());
        //     System.out.println(searchResponse.hits().hits().size());
        //     System.out.println(searchResponse.hits().hits().get(0).source());
        // } catch (BeansException | OpenSearchException | IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // CuisineRepository repository = applicationContext.getBean(CuisineRepository.class);84700.1

        // Cuisine c1 = new Cuisine(1L, null, null);
        // repository.remove(c1);
    }
}
