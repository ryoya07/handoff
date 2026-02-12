package com.example.handoff;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.handoff.domain.Handoff;
import com.example.handoff.repository.HandoffMapper;

@SpringBootTest
@Transactional
class HandoffEncodingTest {

    @Autowired
    private HandoffMapper handoffMapper;

    @Test
    void 日本語が文字化けせずに保存取得できる() {
        // given
        Handoff handoff = new Handoff();
        handoff.setTitle("テスト引き継ぎ");
        handoff.setContent("これは日本語のテストです。漢字もOK。");
        handoff.setCreatedBy(4L);

        // when
        handoffMapper.insert(handoff);
        Handoff saved = handoffMapper.findById(handoff.getId());

        // then
        assertThat(saved.getTitle()).isEqualTo("テスト引き継ぎ");
        assertThat(saved.getContent()).isEqualTo("これは日本語のテストです。漢字もOK。");
    }
}
