Commands to run elasticsearch:
1. docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.9
2. docker run -p 9200:9200 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.9