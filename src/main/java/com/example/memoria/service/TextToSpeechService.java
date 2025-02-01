package com.example.memoria.service;

import com.google.cloud.texttospeech.v1.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TextToSpeechService {

    // 받은 string을 음성 파일로 반환
    // 받은 string을 음성 파일로 반환
    public byte[] synthesizeSpeech(String text) throws IOException {
        // 서비스 계정 키 파일 경로를 시스템 환경 변수에서 가져오기
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        // 인증 정보 로드
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
            credentials = GoogleCredentials.fromStream(serviceAccountStream);
        }

        // Text-to-Speech 클라이언트 생성
        TransportChannelProvider channelProvider = InstantiatingGrpcChannelProvider.newBuilder().build();
        TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
                .setTransportChannelProvider(channelProvider)
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(settings)) {

            // 요청 내용을 구성
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // 음성 설정 (언어 코드, 음성 유형 등)
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US") // 언어 설정
                    .setName("en-US-Neural2-A") // 특정 음성 이름 설정
                    .build();

            // 오디오 출력 형식 설정
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3) // MP3 형식
                    .build();

            // TTS 요청
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // 오디오 데이터를 바이트 배열로 가져오기
            ByteString audioContents = response.getAudioContent();
            return audioContents.toByteArray(); // 음성 데이터(byte[]) 반환
        }
    }

    // 받은 string을 로컬 폴더에 저장
    public String synthesizeSpeechToFile(String text) throws IOException {
        // 사용자 바탕화면 경로를 얻고, tts 폴더를 설정
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + "/Desktop"; // 바탕화면 경로
        String ttsFolderPath = desktopPath + "/tts"; // tts 폴더 경로

        // tts 폴더가 없으면 생성
        File ttsFolder = new File(ttsFolderPath);
        if (!ttsFolder.exists()) {
            ttsFolder.mkdir(); // 폴더 생성
        }

        // 현재 시각을 파일 이름에 포함시켜 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String currentDateTime = LocalDateTime.now().format(formatter);
        String outputFileName = ttsFolderPath + "/output_" + currentDateTime + ".mp3"; // 현재 시각 포함

        // 시스템 환경 변수에서 서비스 계정 키 파일 경로 가져오기
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (credentialsPath == null) {
            throw new IllegalStateException("환경 변수 'GOOGLE_APPLICATION_CREDENTIALS'가 설정되지 않았습니다.");
        }

        // 인증 정보 로드
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
            credentials = GoogleCredentials.fromStream(serviceAccountStream);
        }

        // Text-to-Speech 클라이언트 생성
        TransportChannelProvider channelProvider = InstantiatingGrpcChannelProvider.newBuilder().build();
        TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
                .setTransportChannelProvider(channelProvider)
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(settings)) {

            // 요청 내용을 구성
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // 음성 설정 (언어 코드, 음성 유형 등)
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US") // 언어 설정
                    .setName("en-US-Neural2-A") // 특정 음성 이름 설정
                    .build();

            // 오디오 출력 형식 설정
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3) // MP3 형식
                    .build();

            // TTS 요청
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // 오디오 데이터 가져오기
            ByteString audioContents = response.getAudioContent();

            // MP3 파일로 저장
            try (FileOutputStream out = new FileOutputStream(outputFileName)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file: " + outputFileName);
            }
        }

        return outputFileName; // 저장된 파일 경로 반환
    }
}
