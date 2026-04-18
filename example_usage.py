#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
自定义单位换算试题生成器的示例
Example of customizing the unit conversion test generator
"""

from generate_unit_conversion_test import create_test_paper

# 示例1：生成默认配置的试题（10页，每页50题）
print("生成示例1：默认配置")
create_test_paper(
    filename="示例1_默认配置.docx",
    pages=10,
    questions_per_page=50
)

# 示例2：生成小测验（3页，每页30题）
print("\n生成示例2：小测验")
create_test_paper(
    filename="示例2_小测验.docx",
    pages=3,
    questions_per_page=30
)

# 示例3：生成大考试（15页，每页60题）
print("\n生成示例3：大考试")
create_test_paper(
    filename="示例3_大考试.docx",
    pages=15,
    questions_per_page=60
)

# 示例4：生成家庭作业（5页，每页40题）
print("\n生成示例4：家庭作业")
create_test_paper(
    filename="示例4_家庭作业.docx",
    pages=5,
    questions_per_page=40
)

print("\n所有示例文件已生成完毕！")
