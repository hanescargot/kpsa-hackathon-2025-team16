import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:dio/dio.dart';

class CameraScreen extends StatefulWidget {
  const CameraScreen({super.key});

  @override
  State<CameraScreen> createState() => _CameraScreenState();
}

class _CameraScreenState extends State<CameraScreen> {
  File? _imageFile;
  bool _isUploading = false;
  final Dio _dio = Dio();

  Future<void> _pickImage() async {
    final picked = await ImagePicker().pickImage(source: ImageSource.camera);

    if (picked != null) {
      setState(() => _imageFile = File(picked.path));
    }
  }

  Future<void> _uploadImageWithDio() async {
    if (_imageFile == null) return;

    setState(() => _isUploading = true);

    try {
      final formData = FormData.fromMap({
        'file': await MultipartFile.fromFile(
          _imageFile!.path,
          filename: _imageFile!.path.split('/').last,
        ),
      });

      final response = await _dio.post(
        'https://your-server.com/upload', // TODO: 서버 주소로 변경
        data: formData,
        options: Options(contentType: 'multipart/form-data'),
      );

      _showDialog('업로드 성공! 응답: ${response.statusCode}');
    } catch (e) {
      _showDialog('업로드 실패: $e');
    } finally {
      setState(() => _isUploading = false);
    }
  }

  void _showDialog(String msg) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        content: Text(msg),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('확인'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('약봉투 촬영')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            if (_imageFile != null)
              Image.file(_imageFile!, height: 300, fit: BoxFit.cover)
            else
              Container(
                height: 300,
                color: Colors.grey.shade300,
                child: const Center(child: Text('촬영한 이미지가 없습니다')),
              ),
            const SizedBox(height: 16),
            ElevatedButton.icon(
              onPressed: _pickImage,
              icon: const Icon(Icons.camera_alt),
              label: const Text('카메라로 촬영'),
            ),
            const SizedBox(height: 8),
            if (_imageFile != null)
              ElevatedButton.icon(
                onPressed: _isUploading ? null : _uploadImageWithDio,
                icon: const Icon(Icons.cloud_upload),
                label: Text(_isUploading ? '업로드 중...' : '서버로 전송'),
              ),
          ],
        ),
      ),
    );
  }
}
