import 'package:camera/camera.dart';
import 'package:flutter/material.dart';

class CameraGuideScreen extends StatefulWidget {
  const CameraGuideScreen({super.key});

  @override
  State<CameraGuideScreen> createState() => _CameraGuideScreenState();
}

class _CameraGuideScreenState extends State<CameraGuideScreen> {
  CameraController? _controller;
  late List<CameraDescription> _cameras;
  bool _isInitialized = false;

  @override
  void initState() {
    super.initState();
    _initializeCamera();
  }

  Future<void> _initializeCamera() async {
    _cameras = await availableCameras();
    _controller = CameraController(_cameras[0], ResolutionPreset.medium);

    await _controller!.initialize();
    setState(() => _isInitialized = true);
  }

  @override
  void dispose() {
    _controller?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isInitialized
          ? Stack(
        children: [
          CameraPreview(_controller!),

          // 빨간 가이드 박스
          Center(
            child: Container(
              width: MediaQuery.of(context).size.width * 0.8,
              height: 220,
              decoration: BoxDecoration(
                border: Border.all(color: Colors.red, width: 3),
                borderRadius: BorderRadius.circular(8),
              ),
            ),
          ),

          // 안내 텍스트
          Positioned(
            bottom: 160,
            left: 0,
            right: 0,
            child: Center(
              child: Text(
                '약봉투 전체를 박스안에 위치시켜 주세요',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 14,
                  fontFamily: 'Maplestory',
                  shadows: [
                    Shadow(
                      color: Colors.black.withOpacity(0.7),
                      blurRadius: 4,
                    ),
                  ],
                ),
              ),
            ),
          ),

          // 촬영 버튼
          Positioned(
            bottom: 30,
            left: 0,
            right: 0,
            child: Center(
              child: FloatingActionButton(
                onPressed: () async {
                  final image = await _controller!.takePicture();
                  // TODO: 촬영한 이미지 처리 (업로드 or 저장)
                  Navigator.of(context).pop();

                },
                backgroundColor: Colors.white,
                child: const Icon(Icons.camera, color: Colors.black),
              ),
            ),
          ),
        ],
      )
          : const Center(child: CircularProgressIndicator()),
    );
  }
}
