Commands to run elasticsearch:
1. docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.9
2. docker run -p 9200:9200 -e "discovery.type=single-node" \
   -e "http.cors.enabled=true" \
   -e "http.cors.allow-origin=http://localhost:8000" \
   -e "http.cors.allow-headers=X-Requested-With,X-Auth-Token,Content-Type,Content-Length,Authorization" \
   -e "http.cors.allow-credentials=true" \
   docker.elastic.co/elasticsearch/elasticsearch:7.17.9
3. docker run -p 9200:9200 -e "discovery.type=single-node" \
   -e "http.cors.enabled=true" \
   -e "http.cors.allow-origin=*" \
   -e "http.cors.allow-headers=X-Requested-With,X-Auth-Token,Content-Type,Content-Length,Authorization" \
   -e "http.cors.allow-credentials=true" \
   docker.elastic.co/elasticsearch/elasticsearch:7.17.9

