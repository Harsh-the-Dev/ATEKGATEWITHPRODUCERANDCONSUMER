package Kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.time.Duration
import com.atek.gate.app.event.manager.runner
import com.squareup.moshi.Json
import org.json.JSONString
import java.util.*

var dataConsumer: String = ""
object ProducerAndConsumer {
    fun Consumer() {
        val bootstrapServers = "127.0.0.1:9092"
        val Group = Math.random().toString()
        val prop = Properties()
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, Group)
        val consumer = KafkaConsumer<Json,Json>(prop)
        consumer.subscribe(setOf("Harsh"))
        while (true) {
            val main = consumer.poll(Duration.ofMillis(100))
//            val records = consumer.poll(Duration.ofMillis(100))
            for (record in main) {
                println("Consumer " + record.value())
            }
        }
    }

    fun Producer() {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        val producer = KafkaProducer<String,String>(properties)
        while (true) {
//            val InputByUser = Scanner(System.`in`)
//            val name = InputByUser.nextLine()
            //            ProducerRecord<String, String> record = new ProducerRecord<>("Harsh", "key1", name);
            producer.send(ProducerRecord("Harsh", dataConsumer))
            producer.flush()
            runner()

        }
    }

    @JvmStatic
    fun senderAndReciver() {
        val mt = ProducerAndConsumer
        val t1 = Thread { Producer() }

        // Create thread t2
        val t2 = Thread { Consumer() }

        // Start both threads
        t1.start()
        t2.start()
    }
}
