package com.example.demo;
import com.google.cloud.vision.v1.AnnotateImageRequest;
    import com.google.cloud.vision.v1.AnnotateImageResponse;
    import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
    import com.google.cloud.vision.v1.EntityAnnotation;
    import com.google.cloud.vision.v1.Feature;
    import com.google.cloud.vision.v1.Feature.Type;
    import com.google.cloud.vision.v1.Image;
    import com.google.cloud.vision.v1.ImageAnnotatorClient;
    import com.google.protobuf.ByteString;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
    public class Vision {
      public Map<String,Float> visionapi() throws Exception {
        // Instantiates a client
        Map<String,Float> map = new HashMap<>();
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
          // The path to the image file to annotate
          String fileName = "./newfile";

          // Reads the image file into memory
          Path path = Paths.get(fileName);
          byte[] data = Files.readAllBytes(path);
          ByteString imgBytes = ByteString.copyFrom(data);

          // Builds the image annotation request
          List<AnnotateImageRequest> requests = new ArrayList<>();
          Image img = Image.newBuilder().setContent(imgBytes).build();
          Feature feat = Feature.newBuilder().setType(Type.IMAGE_PROPERTIES).build();
          AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
              .addFeatures(feat)
              .setImage(img)
              .build();
          requests.add(request);

          // Performs label detection on the image file
          BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
          List<AnnotateImageResponse> responses = response.getResponsesList();
          map.put("red", responses.get(0).getImagePropertiesAnnotation().getDominantColors().getColors(0).getColor().getRed());
          map.put("green", responses.get(0).getImagePropertiesAnnotation().getDominantColors().getColors(0).getColor().getGreen());
          map.put("blue", responses.get(0).getImagePropertiesAnnotation().getDominantColors().getColors(0).getColor().getBlue());
         
        }
        return map;
      }
    }