import 'dart:io';

import 'package:dio/dio.dart';
import 'package:http_parser/http_parser.dart';

import '../models/ocr_model.dart';

String? globalAccessToken;

class ApiService {
  final Dio _dio = Dio(
    BaseOptions(
      baseUrl: 'http://3.35.14.212:8080',
      connectTimeout: const Duration(seconds: 10),
      receiveTimeout: const Duration(seconds: 10),
    ),
  );

  Future<void> uploadOcrImage(File imageFile) async {
        print("!!!!!! $globalAccessToken");

    try {
      final fileName = imageFile.path.split('/').last;

      final formData = FormData.fromMap({
        'file': await MultipartFile.fromFile(
          imageFile.path,
          filename: fileName,
          contentType: MediaType('image', 'jpeg'),
        ),
      });

      final response = await _dio.post(
        '/api/prescription/ocr',
        data: formData,
        options: Options(
          headers: {
            'Authorization': 'Bearer $globalAccessToken',
            'accept': '*/*',
            // 'Content-Type': 생략
          },
        ),
      );

      print('✅ 업로드 성공: ${response.data}');
    } catch (e) {
      print('❌ 업로드 실패: $e');
      rethrow;
    }
  }
  // Future<void> uploadOcrImage(File imageFile) async {
  //   try {
  //     print("!!!!!! $globalAccessToken");
  //     final fileName = imageFile.path.split('/').last;
  //
  //     FormData formData = FormData.fromMap({
  //       'file': await MultipartFile.fromFile(
  //         imageFile.path,
  //         filename: fileName,
  //         contentType: MediaType('image', 'jpeg'),
  //       ),
  //     });
  //
  //     final response = await _dio.post(
  //       '/api/prescription/ocr',
  //       data: formData,
  //       options: Options(
  //         headers: {
  //           'Authorization': 'Bearer $globalAccessToken',
  //           'Content-Type': 'multipart/form-data',
  //           'accept': '*/*',
  //         },
  //       ),
  //     );
  //
  //     print('✅ 업로드 성공: ${response.data}');
  //   } catch (e) {
  //     print('❌ 업로드 실패: $e');
  //     rethrow;
  //   }
  // }

  Future<Map<String, dynamic>> login({
    required String username,
    required String password,
  }) async {
    try {
      final response = await _dio.post(
        '/api/auth/login',
        data: {'email': username, 'password': password},
      );
      globalAccessToken = response.data['result']['token']; // 전역 변수에 저장!

      // ✅ 성공 로그
      print('✅ 로그인 성공: ${response.data['result']['token']}');
      return response.data;
    } on DioException catch (e) {
      // ❌ HTTP 에러
      print('❌ Dio 에러: ${e.response?.statusCode} - ${e.response?.data}');
      throw Exception('로그인 실패: ${e.response?.data['message'] ?? '알 수 없는 오류'}');
    } catch (e) {
      // ❌ 기타 에러
      print('❌ 예외 발생: $e');
      throw Exception('로그인 중 문제가 발생했습니다.');
    }
  }
}
