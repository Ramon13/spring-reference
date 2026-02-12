package com.javamoon.spring_algaworks;

// import java.io.IOException;

// import javax.net.ssl.SSLContext;
// import javax.net.ssl.SSLEngine;

// import org.apache.hc.client5.http.auth.AuthScope;
// import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
// import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
// import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
// import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
// import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
// import org.apache.hc.core5.http.HttpHost;
// import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
// import org.apache.hc.core5.reactor.ssl.TlsDetails;
// import org.apache.hc.core5.ssl.SSLContextBuilder;
// import org.opensearch.client.json.jackson.JacksonJsonpMapper;
// import org.opensearch.client.opensearch.OpenSearchClient;
// import org.opensearch.client.opensearch._types.OpenSearchException;
// import org.opensearch.client.opensearch.cluster.HealthResponse;
// import org.opensearch.client.transport.OpenSearchTransport;
// import org.opensearch.client.transport.httpclient5.ApacheHttpClient5Transport;
// import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
public class ConfigurationApp {

//     @Bean
//     public OpenSearchClient test() throws Exception{
//         HttpHost host = new HttpHost("https", "localhost", 9200);

//         final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//         credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials("admin", "Foobar#9876".toCharArray()));


//         final SSLContext sslcontext = SSLContextBuilder
//                 .create()
//                 .loadTrustMaterial(null, (chains, authType) -> true)
//                 .build();

//         ApacheHttpClient5Transport transport = ApacheHttpClient5TransportBuilder.builder(host)
//             .setHttpClientConfigCallback(httpClientBuilder -> {
//             final TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
//                     .setSslContext(sslcontext)
//                     .build();

//             final PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder
//                     .create()
//                     .setTlsStrategy(tlsStrategy)
//                     .build();

//             return httpClientBuilder
//                     .setDefaultCredentialsProvider(credentialsProvider)
//                     .setConnectionManager(connectionManager);
//         }).build();

//         OpenSearchClient client = new OpenSearchClient(transport);
//         return client;
//     }
}
