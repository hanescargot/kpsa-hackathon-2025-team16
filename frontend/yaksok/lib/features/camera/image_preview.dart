import 'dart:io';
import 'package:flutter/material.dart';
import '../../models/ocr_model.dart';
import '../../service/app_service.dart';
import '../../util.dart';
import 'camera_screen.dart';
import 'ocr_result_screen.dart';

class ImagePreviewScreen extends StatelessWidget {
  final File imageFile;

  const ImagePreviewScreen({super.key, required this.imageFile});

  @override
  Widget build(BuildContext context) {
    final themeColor = kColorPrimary;

    return Scaffold(
      body: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.all(20),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // const SizedBox(height: 80), // 상단 공간 확보
                /// 미리보기 카드
                Card(
                  elevation: 4,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(12),
                    child: Image.file(
                      imageFile,
                      height: 220,
                      width: double.infinity,
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
                const SizedBox(height: 24),

                /// 안내 텍스트
                const Text(
                  '촬영한 약봉투 이미지가 올바르게 보이나요?',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: 80),

                /// 버튼 2개
                Row(
                  children: [
                    // 다시 촬영하기
                    Expanded(
                      child: OutlinedButton(
                        style: OutlinedButton.styleFrom(
                          foregroundColor: themeColor,
                          side: BorderSide(color: themeColor),
                          padding: const EdgeInsets.symmetric(vertical: 14),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                        ),
                        onPressed: () => Navigator.of(context).pushReplacement(
                          MaterialPageRoute(
                            builder: (_) => const CameraGuideScreen(),
                          ),
                        ),
                        child: const Text(
                          '다시 촬영하기',
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                    const SizedBox(width: 16),
                    // 생성 시작
                    Expanded(
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: themeColor,
                          padding: const EdgeInsets.symmetric(vertical: 14),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                        ),
                        onPressed: () async {
                          showDialog(
                            context: context,
                            barrierDismissible: false, // 뒤로가기 방지
                            builder: (context) => Dialog(
                              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                              backgroundColor: Colors.white,
                              child: Padding(
                                padding: const EdgeInsets.all(24),
                                child: Column(
                                  mainAxisSize: MainAxisSize.min,
                                  children: [
                                    const SizedBox(height: 12),
                                    const CircularProgressIndicator(
                                      valueColor: AlwaysStoppedAnimation<Color>(Colors.blueAccent),
                                    ),
                                    const SizedBox(height: 24),
                                    const Text(
                                      "AI가 약봉투 정보를 분석 중입니다.",
                                      style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 16,
                                        fontFamily: 'MapleStory',
                                      ),
                                      textAlign: TextAlign.center,
                                    ),
                                    const SizedBox(height: 12),
                                    const Text(
                                      "최대 1분 정도 소요될 수 있습니다.",
                                      style: TextStyle(
                                        fontSize: 14,
                                        color: Colors.black54,
                                        fontFamily: 'MapleStory',
                                      ),
                                      textAlign: TextAlign.center,
                                    ),
                                    const SizedBox(height: 8),
                                  ],
                                ),
                              ),
                            ),
                          );


                          try {

                            final result = await ApiService().uploadOcrImage(imageFile);

                            if (context.mounted) {
                              Navigator.of(context).pop(); // 로딩 다이얼로그 닫기
                              Navigator.of(context).pop(); // 로딩 다이얼로그 닫기

                          Navigator.of(context).push(
                                MaterialPageRoute(
                                  builder: (_) => OcrResultScreen(
                                    result: OcrResult.fromJson(result),
                                  ),
                                ),
                              );
                            }
                          } catch (e) {
                            if (context.mounted)
                              Navigator.of(context).pop(); // 에러 시도 닫기
                            showDialog(
                              context: context,
                              builder: (context) => AlertDialog(
                                title: const Text('오류'),
                                content: Text('처리 중 오류가 발생했어요\n\n$e'),
                                actions: [
                                  TextButton(
                                    onPressed: () =>
                                        Navigator.of(context).pop(),
                                    child: const Text('확인'),
                                  ),
                                ],
                              ),
                            );
                          }
                        },
                        child: const Text(
                          '생성 시작',
                          style: TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),

          /// 🏠 홈 버튼
          Positioned(
            top: 40,
            left: 16,
            child: IconButton(
              icon: Icon(Icons.home, size: 28, color: kColorPrimary),
              onPressed: () => Navigator.of(context).pop(),
            ),
          ),

          /// 📌 상단 제목
          Positioned(
            top: 45,
            left: 60,
            right: 60,
            child: Center(
              child: Text(
                '복용 스케줄 생성',
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: kColorPrimary,
                  fontSize: 18,
                  fontFamily: 'MapleStory',
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
