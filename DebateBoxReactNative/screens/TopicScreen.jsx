import React, { useState, useEffect } from "react";
import {
  Text,
  View,
  StyleSheet,
  Image,
  ScrollView,
  TextInput,
  Button,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useForm, Controller } from "react-hook-form";
import { MYAXIOS } from "../service/Helper";

function TopicScreen({ route }) {
  const { id, image } = route.params;
  const [topic, setTopic] = useState([]);
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm();

  useEffect(() => {
    fetchOpinions();
  }, []);

  const fetchOpinions = async () => {
    const token = await AsyncStorage.getItem("token");

    try {
      const response = await MYAXIOS.get(`/api/topic/${id}`, {
        headers: {
          Authorization: "Bearer " + token,
        },
      });
      setTopic(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("Error fetching opinions:", error);
    }
  };

  const submitOpinion = async (data) => {
    const token = await AsyncStorage.getItem("token");

    try {
      await MYAXIOS.post(
        "/api/submitOpinion",
        {
          id: id,
          opinionText: data.opinionText,
        },
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );
      fetchOpinions();
      reset();
    } catch (error) {
      console.error("Error submitting opinion:", error);
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>{topic.topicName}</Text>
      <Image source={image} style={styles.image} />
      <Text style={styles.cardTitle}>{topic.topicDescription}</Text>
      <View style={styles.card}>
        <Text style={styles.cardTitle}>Topic Opinions</Text>
        <ScrollView style={styles.opinionsContainer} nestedScrollEnabled={true}>
          {Array.isArray(topic.list) && topic.list.length > 0 ? (
            topic.list.map((opinion, index) => (
              <View key={index} style={styles.opinionItem}>
                <Text style={styles.opinionUser}>{opinion.userName}</Text>
                <Text style={styles.opinionText}>{opinion.opinionText}</Text>
                <Text style={styles.opinionTime}>{opinion.submissionTime}</Text>
              </View>
            ))
          ) : (
            <Text>No opinions available</Text>
          )}
        </ScrollView>
      </View>

      <View style={styles.card}>
        <Text style={styles.cardTitle}>My Opinion</Text>
        <View style={styles.chatContainer}>
          <Controller
            control={control}
            render={({ field: { onChange, onBlur, value } }) => (
              <TextInput
                style={styles.input}
                placeholder="Type your message here..."
                multiline={true}
                onBlur={onBlur}
                onChangeText={onChange}
                value={value}
              />
            )}
            name="opinionText"
            rules={{ required: true }}
            defaultValue=""
          />
          {errors.opinionText && (
            <Text style={styles.error}>Text is required.</Text>
          )}
          <Button
            title="Send"
            onPress={handleSubmit(submitOpinion)}
            disabled={isSubmitting}
          />
        </View>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    paddingHorizontal: 20,
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 10,
  },
  image: {
    width: 200,
    height: 200,
    marginBottom: 10,
    borderRadius: 10,
  },
  description: {
    fontSize: 16,
    textAlign: "center",
    marginBottom: 20,
  },
  card: {
    width: "100%",
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 5,
    padding: 10,
    marginBottom: 20,
  },
  cardTitle: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 10,
  },
  opinionsContainer: {
    maxHeight: 200,
    overflowY: "scroll",
  },
  opinionItem: {
    marginBottom: 5,
  },
  opinionUser: {
    fontWeight: "bold",
  },
  opinionText: {},
  opinionTime: {
    color: "#666",
    fontSize: 12,
  },
  chatContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  input: {
    flex: 1,
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 5,
    padding: 10,
    marginRight: 10,
  },
  sendButton: {
    backgroundColor: "blue",
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
  },
  sendButtonText: {
    color: "#fff",
    fontWeight: "bold",
  },
  error: {
    color: "red",
    marginTop: 5,
  },
});

export default TopicScreen;
