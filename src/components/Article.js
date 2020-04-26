import React from 'react'
import styled from 'styled-components'
import Bio from './Bio'
import Content from './Content'
import ArticleFooter from '../components/ArticleFooter';

const ArticleWrapper = styled.article`
  padding: 0 30px 30px;

  @media only screen and (max-width: 500px) {
    padding: 0;
  }
`;

class Article extends React.Component {
  render() {
    const { post, md } = this.props;
    const { frontmatter } = post;
    return (
      <ArticleWrapper>
        <Content
          content={post.html || post}
          date={frontmatter && frontmatter.date}
          tags={frontmatter && frontmatter.tags}
          translations={frontmatter && frontmatter.translations}
          md={md}
        />
        <ArticleFooter>
          <Bio />
        </ArticleFooter>
      </ArticleWrapper>
    )
  }
}

export default Article
