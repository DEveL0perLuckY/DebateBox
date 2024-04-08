import React from "react";
import {
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  ImageBackground,
} from "react-native";

function HomeScreen({ navigation }) {
  const topics = [
    { id: 10003, name: "Climate Change", image: require("../assets/img2.jpg") },
    {
      id: 10004,
      name: "Universal Basic Income (UBI)",
      image: require("../assets/img1.jpg"),
    },
    {
      id: 10005,
      name: "Artificial Intelligence Ethics",
      image: require("../assets/img3.jpg"),
    },
    {
      id: 10006,
      name: "Healthcare Reform",
      image: require("../assets/img4.jpg"),
    },
  ];

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Debate Topics</Text>
      {topics.map((topic) => (
        <TouchableOpacity
          key={topic.id}
          style={styles.topicContainer}
          onPress={() =>
            navigation.navigate("TopicScreen", {
              id: topic.id,
              name: topic.name,
              image: topic.image,
              description: "topic.description",
            })
          }
        >
          <ImageBackground
            source={topic.image}
            style={styles.image}
            imageStyle={{ borderRadius: 10 }}
          >
            <View style={styles.overlay}>
              <Text style={styles.topicName}>{topic.name}</Text>
            </View>
          </ImageBackground>
        </TouchableOpacity>
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  topicContainer: {
    width: "80%",
    marginBottom: 20,
  },
  image: {
    width: "100%",
    height: 200,
    justifyContent: "flex-end",
    alignItems: "center",
    resizeMode: "cover",
  },
  overlay: {
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    width: "100%",
    alignItems: "center",
    padding: 10,
    borderBottomLeftRadius: 10,
    borderBottomRightRadius: 10,
  },
  topicName: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#fff",
  },
});

export default HomeScreen;
