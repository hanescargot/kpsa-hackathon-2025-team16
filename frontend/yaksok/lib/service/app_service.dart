import 'package:dio/dio.dart';

String? globalAccessToken;

class ApiService {
  final Dio _dio = Dio(
    BaseOptions(
      baseUrl: 'http://3.35.14.212:8080',
      connectTimeout: const Duration(seconds: 10),
      receiveTimeout: const Duration(seconds: 10),
    ),
  );

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
